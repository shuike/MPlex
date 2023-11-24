package com.skit.mplex

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.LoadEventInfo
import androidx.media3.exoplayer.source.MediaLoadData
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import com.skit.mplex.databinding.ActivityTestBinding
import com.skit.mplex.ktx.dp
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


@OptIn(UnstableApi::class)

class TestActivity : AppCompatActivity() {

    private val TAG = "TestActivity"
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var binding: ActivityTestBinding
    private lateinit var trackSelector: DefaultTrackSelector
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var httpDataSource: DefaultHttpDataSource

    private val allSubTitles = LinkedList<SubtitlesData>()
    private val remoteSubtitles = LinkedList<SubtitlesData.Remote>()
    private val subtitlesSelectManager = SubtitlesSelectManager()
    private val subtitlesSelector = SubtitlesSelector(subtitlesSelectManager)

    private val plexLocalApi: PlexLocalApi by lazy { HttpFactory.localRetrofit.create(PlexLocalApi::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.playerParent.apply {
            setDuration(100000)
            textStrCallback = { isLeft, second ->
                "$second"
            }
            background = ColorDrawable(Color.RED)
            addView(Button(this@TestActivity).apply {
                text = "Text"
                layoutParams = ViewGroup.LayoutParams(
                    100.dp,
                    50.dp
                )
            })
        }
        // 不显示字幕按钮
        binding.exoPlayerView.setShowSubtitleButton(false)

        initExoPlayer()
        lifecycleScope.launch {
            val tvShowChildMetaData = plexLocalApi.getMovieMetaData("241")
            val metadata = tvShowChildMetaData.mediaContainer.metadata[0]
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
                            it.key.plexUrlAddToken()
                        )
                    )
                    allSubTitles.addAll(remoteSubtitles)
                }
            }
            val plexUrl = part.key.plexUrl()
            play(plexUrl)
        }
        findViewById<View>(R.id.mplex_exo_subtitle).setOnClickListener {
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
//        val videoPath = "https://www.runoob.com/try/demo_source/movie.mp4"
//        play(videoPath)
    }

    private fun initExoPlayer() {
        httpDataSource = createHttpDataSource().apply {
            setRequestProperty("X-Plex-Token", HttpFactory.PLEX_TOKEN)
        }
        dataSourceFactory = DataSource.Factory { httpDataSource }
        trackSelector = DefaultTrackSelector(this, AdaptiveTrackSelection.Factory())
        exoPlayer = ExoPlayer.Builder(this)
            .setRenderersFactory(mRenderFactory)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(this).setDataSourceFactory(dataSourceFactory)
            )
            .setTrackSelector(trackSelector)
            .build()
        exoPlayer.playWhenReady = true
        binding.exoPlayerView.player = exoPlayer
    }

    private fun play(videoPath: String) {
        Log.d(TAG, "play: ${videoPath}")
        val videoSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPath))
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

        exoPlayer.addListener(object : Player.Listener {
            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == ExoPlayer.STATE_READY) {
                    prepareSubTitles(exoPlayer)
                }
                binding.text.append("onPlaybackStateChanged: ${playbackState}\n")
            }
        })
        exoPlayer.addAnalyticsListener(object : AnalyticsListener {
            override fun onTracksChanged(eventTime: AnalyticsListener.EventTime, tracks: Tracks) {
                super.onTracksChanged(eventTime, tracks)
                binding.text.append("onTracksChanged\n")
            }

            override fun onIsLoadingChanged(
                eventTime: AnalyticsListener.EventTime,
                isLoading: Boolean
            ) {
                super.onIsLoadingChanged(eventTime, isLoading)
            }

            override fun onPlayWhenReadyChanged(
                eventTime: AnalyticsListener.EventTime,
                playWhenReady: Boolean,
                reason: Int
            ) {
                super.onPlayWhenReadyChanged(eventTime, playWhenReady, reason)
                binding.text.append("onPlayWhenReadyChanged\n")
            }

            override fun onLoadCompleted(
                eventTime: AnalyticsListener.EventTime,
                loadEventInfo: LoadEventInfo,
                mediaLoadData: MediaLoadData
            ) {
                super.onLoadCompleted(eventTime, loadEventInfo, mediaLoadData)
//                initSubTitle(exoPlayer)
            }
        })
        exoPlayer.prepare()

    }

    private val playerSubtitles = mutableListOf<Tracks.Group>()
    fun prepareSubTitles(exoPlayer: ExoPlayer) {
        playerSubtitles.clear()

        binding.text.text = ""
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
        groupList.forEach {
            val mediaTrackGroup = it.mediaTrackGroup
            val format = mediaTrackGroup.getFormat(0)
            val title = SubtitlesUtils.getName(format)
            binding.text.append(
                title + " ${format.id} " + format.toString() + " ${mediaTrackGroup.length} \n"
            )
        }

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

    private fun createHttpDataSource(): DefaultHttpDataSource {
        val bandwidthMeter = DefaultBandwidthMeter.Builder(this).build()
        val httpDataSource = DefaultHttpDataSource.Factory().apply {
            setTransferListener(bandwidthMeter)
            setUserAgent(Util.getUserAgent(this@TestActivity, getString(R.string.app_name)))
        }.createDataSource()
        return httpDataSource
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

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.exoPlayerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}