package com.skit.mplex.ui.play

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.decoder.ffmpeg.FfmpegAudioRenderer
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.ui.CaptionStyleCompat
import androidx.media3.ui.PlayerControlView
import com.skit.mplex.MTrackNameProvider
import com.skit.mplex.bean.MovieMetaDataResponse
import com.skit.mplex.bean.TVShowChildMetaDataResponse
import com.skit.mplex.databinding.ActivityPlayerBinding
import com.skit.mplex.ktx.launch
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.net.plexUrl
import com.skit.mplex.net.plexUrlAddToken
import com.skit.mplex.server.PlexLocalApi
import com.skit.mplex.subtitles.SubtitlesData
import com.skit.mplex.subtitles.SubtitlesSelectManager
import com.skit.mplex.subtitles.SubtitlesUtils
import com.skit.mplex.view.subtitles.SubtitlesSelector
import kotlinx.coroutines.launch
import java.util.LinkedList
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@UnstableApi
class PlayerActivity : AppCompatActivity() {
    companion object {
        private var movieData: MovieMetaDataResponse.MediaContainer.Metadata? = null
        private var tvShowData: TVShowChildMetaDataResponse.MediaContainer.Metadata? = null

        fun launch(
            context: Context,
            metadata: TVShowChildMetaDataResponse.MediaContainer.Metadata
        ) {
            tvShowData = metadata
            context.launch<PlayerActivity> {}
        }

        fun launch(context: Context, metadata: MovieMetaDataResponse.MediaContainer.Metadata) {
            movieData = metadata
            context.launch<PlayerActivity> {}
        }
    }

    private val mRenderFactory: DefaultRenderersFactory by lazy {
        object : DefaultRenderersFactory(applicationContext) {
            override fun buildAudioRenderers(
                context: Context,
                extensionRendererMode: Int,
                mediaCodecSelector: MediaCodecSelector,
                enableDecoderFallback: Boolean,
                audioSink: AudioSink,
                eventHandler: Handler,
                eventListener: AudioRendererEventListener,
                out: ArrayList<Renderer>,
            ) {
                //添加FfmpegAudioRenderer
                out.add(FfmpegAudioRenderer())
                super.buildAudioRenderers(
                    context,
                    extensionRendererMode,
                    mediaCodecSelector,
                    enableDecoderFallback,
                    audioSink,
                    eventHandler,
                    eventListener,
                    out
                )
            }
        }.apply {
            setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)
        }

    }

    private var exoPlayer: ExoPlayer? = null
    private lateinit var mBinding: ActivityPlayerBinding

    // 包含内嵌与远程外挂的字幕
    private val allSubTitles = LinkedList<SubtitlesData>()

    // 远程外挂字幕
    private val remoteSubtitles = LinkedList<SubtitlesData.Remote>()
    private val subtitlesSelectManager = SubtitlesSelectManager()
    private val subtitlesSelector = SubtitlesSelector(subtitlesSelectManager)

    private lateinit var trackSelector: DefaultTrackSelector
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var httpDataSource: DefaultHttpDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPlayerBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        val controlView = findViewById<PlayerControlView>(androidx.media3.ui.R.id.exo_controller)
        val javaClass = controlView.javaClass
        val declaredField = javaClass.getDeclaredField("trackNameProvider")
        declaredField.isAccessible = true
        declaredField.set(controlView, MTrackNameProvider())

        mBinding.root.setOnTouchListener { v, event ->

            return@setOnTouchListener true
        }
//        val javaClass = styledPlayerControlView.javaClass
//        val settingsWindowField = javaClass.getDeclaredField("settingsWindow")
//        settingsWindowField.isAccessible = true
//        val fieldClass = settingsWindowField
//        val declaredMethod = fieldClass.getDeclaredMethod("setFocusable", Boolean::class.java)
//        declaredMethod.invoke(settingsWindowField, false)
        mBinding.exoPlayerView.setShowSubtitleButton(false)
        mBinding.playerParent.apply {
            bindWindow(window)
            seekCallback = { second ->
                exoPlayer?.let {
                    exoPlayer?.seekTo(it.currentPosition + (second * 1000))
                }
            }
            textStrCallback = { isLeft, second ->
                val text = exoPlayer?.let { exoPlayer ->
                    val endSecond = exoPlayer.currentPosition + (second * 1000)
                    when {
                        endSecond >= exoPlayer.duration -> {
                            exoPlayer.duration.toDurationText()
                        }

                        endSecond <= 0 -> {
                            ""
                        }

                        else -> {
                            endSecond.toDurationText()
                        }
                    }
                } ?: ""
                text.ifEmpty { "00:00:00" }
            }
        }


        findViewById<View>(com.skit.mplex.R.id.mplex_exo_subtitle).setOnClickListener {
            subtitlesSelector.show(
                this,
                allSubTitles
            )
        }
        subtitlesSelector.onSelect = {
            subtitlesSelectManager.selectSubtitles(
                trackSelector,
                it,
                playerSubtitles
            )
            subtitlesSelector.hide()
        }
    }

    private fun Long.toDurationText(): String {
        val duration = toDuration(DurationUnit.MILLISECONDS)
        val hours = duration.inWholeHours
        var minutes = duration.inWholeMinutes
        var second = duration.inWholeSeconds
        if (hours > 0) {
            minutes -= hours * 60
        }
        if (minutes > 0) {
            second -= minutes * 60
        }
        return "%02d:%02d:%02d".format(hours, minutes, second)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    public override fun onPause() {
        super.onPause()
        pausePlayer()
    }

//    private val mPlexRepository: PlexRepository = PlexRepositoryImpl()

    private val plexServer: PlexLocalApi by lazy {
        HttpFactory.localRetrofit.create(PlexLocalApi::class.java)
    }

    override fun onStart() {
        super.onStart()
        if (exoPlayer == null) {
            val movieData = movieData
            val tvShowData = tvShowData
            if (tvShowData != null) {
                lifecycleScope.launch {
//                    val tvShowChildMetaData = plexServer.getTvShowChildMetaData("682")
                    val metadata = tvShowData //tvShowChildMetaData.mediaContainer.metadata[0]
                    val media = metadata.media[0]
                    val part = media.part[0]
                    val stream = part.stream
                    stream.filter { it.streamType == 3 }.forEach {
                        val streamType = it.streamType // 1:视频 2:音频 3:字幕
                        if (it.key != null) {
                            remoteSubtitles.add(
                                SubtitlesData.Remote(
                                    it.id.toString(),
                                    it.codec,
                                    it.languageTag ?: "",
                                    it.displayTitle,
                                    it.key.plexUrlAddToken(),
                                    it.selected
                                )
                            )
                            allSubTitles.addAll(remoteSubtitles)
                        }
//                binding.text.append("${it.id} ${it.codec} ${it.default} ${it.selected} ${it.languageTag} ${it.displayTitle} ${it.key}\n\n")
                    }
                    initializePlayer(metadata.media[0].part[0].key.plexUrl())
                }
            }

            if (movieData != null) {
                lifecycleScope.launch {
//                    val movieMetaData = plexServer.getMovieMetaData(movieData.media[0].part[0].key)
                    val metadata = movieData // movieMetaData.mediaContainer.metadata[0]
                    val media = metadata.media[0]
                    val part = media.part[0]
                    val stream = part.stream
                    stream.filter { it.streamType == 3 }.forEach {
                        val streamType = it.streamType // 1:视频 2:音频 3:字幕
                        if (it.key != null) {
                            remoteSubtitles.add(
                                SubtitlesData.Remote(
                                    it.id.toString(),
                                    it.codec,
                                    it.languageTag ?: "",
                                    it.displayTitle,
                                    it.key.plexUrlAddToken(),
                                    it.selected
                                )
                            )
                            allSubTitles.addAll(remoteSubtitles)
                        }
//                binding.text.append("${it.id} ${it.codec} ${it.default} ${it.selected} ${it.languageTag} ${it.displayTitle} ${it.key}\n\n")
                    }
                    initializePlayer(metadata.media[0].part[0].key.plexUrl())
                }
            }
        } else {
            exoPlayer!!.play()
        }
    }

    public override fun onStop() {
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        movieData = null
        tvShowData = null
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode in arrayOf(KeyEvent.KEYCODE_ESCAPE, KeyEvent.KEYCODE_BACK)) {
            when {
                subtitlesSelector.isShown -> {
                    subtitlesSelector.hide()
                    return true
                }

                mBinding.exoPlayerView.findViewById<View>(androidx.media3.ui.R.id.exo_controller).isVisible -> {
                    mBinding.exoPlayerView.hideController()
                    return true
                }
//
//                else -> {
//                    finish()
//                    true
//                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    @OptIn(UnstableApi::class)
    private fun initializePlayer(path: String) {
        httpDataSource = httpDataSource(this).apply {
            setRequestProperty("X-Plex-Token", HttpFactory.PLEX_TOKEN)
        }
        dataSourceFactory = DataSource.Factory { httpDataSource }
        trackSelector = DefaultTrackSelector(this, AdaptiveTrackSelection.Factory())
        val exoPlayer = ExoPlayer.Builder(this)
            .setRenderersFactory(mRenderFactory)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(this).setDataSourceFactory(dataSourceFactory)
            )
            .setTrackSelector(trackSelector)
            .build()
        exoPlayer.playWhenReady = true
        this.exoPlayer = exoPlayer

        mBinding.exoPlayerView.subtitleView?.apply {
            setStyle(
                CaptionStyleCompat(
                    Color.WHITE,
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                    CaptionStyleCompat.EDGE_TYPE_OUTLINE,
                    Color.BLACK,
                    null
                )
            );
            setFixedTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
        }
        mBinding.exoPlayerView.player = exoPlayer
        mBinding.exoPlayerView.controllerAutoShow = false
        mBinding.exoPlayerView.setErrorMessageProvider {
            val exoPlaybackException = it as ExoPlaybackException
            it.printStackTrace()
            Pair(0, "${exoPlaybackException.errorCode}")
        }
//        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//            .createMediaSource(MediaItem.fromUri(path))
//        val mergingMediaSource = MergingMediaSource(videoSource)
//        exoPlayer.setMediaSource(mergingMediaSource)

        val videoSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(path))
        val map = remoteSubtitles.mapIndexed { index, remote ->
            SubtitlesUtils.getTextMediaSource(
                remote.id,
                Uri.parse(remote.url),
                remote.codec,
                remote.language
            )
        }.toTypedArray()
        val mergingMediaSource = MergingMediaSource(videoSource, *map)
        exoPlayer.setMediaSource(mergingMediaSource)
        exoPlayer.playWhenReady = playWhenReady
        exoPlayer.seekTo(currentItem, playbackPosition)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        val trackSelectionParameters = exoPlayer.trackSelectionParameters
//        exoPlayer.trackSelectionParameters = trackSelectionParameters
//            .buildUpon()
//            .setPreferredTextLanguages("zh-CN", "zh-HK", "zh-TW")
//            .setPreferredAudioLanguages("en-US")
//            .build()
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == ExoPlayer.STATE_READY) {
                    prepareSubTitles(exoPlayer)
                    mBinding.playerParent.setDuration(exoPlayer.duration)
                }
            }
        })
    }

    private fun httpDataSource(context: Context): DefaultHttpDataSource {
        val bandwidthMeter = DefaultBandwidthMeter.Builder(this).build()
        val httpDataSource = DefaultHttpDataSource.Factory().apply {
            setTransferListener(bandwidthMeter)
            setUserAgent(
                Util.getUserAgent(
                    context,
                    getString(com.skit.mplex.R.string.app_name)
                )
            )
        }.createDataSource()
        return httpDataSource
    }

    private val playerSubtitles = mutableListOf<Tracks.Group>()
    fun prepareSubTitles(exoPlayer: ExoPlayer) {
        playerSubtitles.clear()
        val currentTracks = exoPlayer.currentTracks
        val groups = currentTracks.groups
        val groupList = mutableListOf<Tracks.Group>()
        groups.forEachIndexed { index, group ->
            if (group.type == C.TRACK_TYPE_TEXT) { // 获取字幕轨道
                groupList.add(group)
                playerSubtitles.add(group)
                val trackFormat = group.getTrackFormat(0)
                val id = trackFormat.id
                if (!id.isNullOrEmpty()) {
                    var find = allSubTitles.find { it is SubtitlesData.Remote && it.id == id }
                    if (find == null) {
                        find = allSubTitles.find { it is SubtitlesData.Inner && it.id == id }
                    }
                    if (find == null) {
                        var name = SubtitlesUtils.getName(trackFormat)
                        if (trackFormat.label != null) {
                            name += "-${trackFormat.label}"
                        }
                        allSubTitles.add(
                            SubtitlesData.Inner(
                                id,
                                trackFormat.language ?: "",
                                name
                            )
                        )
                    }
                }
            }
        }
//        groupList.forEach {
//            val mediaTrackGroup = it.mediaTrackGroup
//            val format = mediaTrackGroup.getFormat(0)
//            val title = SubtitlesUtils.getName(format)
//        }
        findViewById<View>(com.skit.mplex.R.id.mplex_exo_subtitle).isVisible =
            allSubTitles.isNotEmpty()
        if (subtitlesSelectManager.selectedId.isEmpty()) {
            val find = remoteSubtitles.find { it.selected }
            if (find != null) {
                subtitlesSelectManager.selectSubtitles(trackSelector, find, playerSubtitles)
            } else {
                trackSelector.setParameters(
                    trackSelector.parameters.buildUpon()
                        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
                )
            }
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, mBinding.exoPlayerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    private fun pausePlayer() {
        exoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.pause()
        }
    }

    private fun releasePlayer() {
        exoPlayer?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        exoPlayer = null
    }
}