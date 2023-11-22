package com.skit.mplex.ui.play

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Pair
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.fromUri
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.decoder.ffmpeg.FfmpegAudioRenderer
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.source.SingleSampleMediaSource
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.R
import com.skit.mplex.MTrackNameProvider
import com.skit.mplex.databinding.ActivityPlayerBinding
import com.skit.mplex.ktx.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@UnstableApi
class PlayerActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "PlayerActivity"
        private const val PLAY_URL = "url"
        fun launch(context: Context, path: String) {
            context.launch<PlayerActivity> {
                putExtra(PLAY_URL, path)
            }
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
    private val path: String by lazy {
        intent.getStringExtra(PLAY_URL) ?: error("没有路径")
    }
    private var player: ExoPlayer? = null
    private lateinit var mBinding: ActivityPlayerBinding

    private var videoOffset = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPlayerBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        val controlView = findViewById<PlayerControlView>(R.id.exo_controller)
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
        mBinding.playerParent.apply {
            bindWindow(window)
            seekCallback = { second ->
                player?.let {
                    player?.seekTo(it.currentPosition + (second * 1000))
                }
            }
            textStrCallback = { isLeft, second ->
                val text = player?.let { exoPlayer ->
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
    }


    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    public override fun onStop() {
        super.onStop()
        releasePlayer()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode in arrayOf(KeyEvent.KEYCODE_ESCAPE, KeyEvent.KEYCODE_BACK)) {
            if (mBinding.exoPlayerView.findViewById<View>(R.id.exo_controller).isVisible) {
                mBinding.exoPlayerView.hideController()
                return true
            } else {
                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    @OptIn(UnstableApi::class)
    private fun initializePlayer() {
        Log.d(TAG, "initializePlayer: ${path}")
        player = ExoPlayer.Builder(this, mRenderFactory)
            .build()
            .also { exoPlayer ->
                mBinding.exoPlayerView.player = exoPlayer
                mBinding.exoPlayerView.controllerAutoShow = false
                mBinding.exoPlayerView.setErrorMessageProvider {
                    val exoPlaybackException = it as ExoPlaybackException
                    it.printStackTrace()
                    Pair(0, "${exoPlaybackException.errorCode}")
                }

                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)
                // 这是媒体资源，代表要播放的媒体。
                val videoSource: MediaSource =
                    ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(fromUri(path))

                // 开发者字幕资源的格式。
//                val textFormat = Format.createTextSampleFormat(
//                    null,
//                    MimeTypes.APPLICATION_SUBRIP,
//                    null,
//                    Format.NO_VALUE,
//                    Format.NO_VALUE,
//                    Locale.getDefault().language,
//                    null,
//                    Format.OFFSET_SAMPLE_RELATIVE
//                )
                val url =
                    "http://192.168.31.32:32400/library/streams/9091?X-Plex-Token=xMdhFuTH6wue8_2L_o6f"
                val textMediaSource: MediaSource =
                    SingleSampleMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(
                            MediaItem.SubtitleConfiguration.Builder(Uri.parse(url))
                                .setLanguage("zh")
                                .setUri(Uri.parse(url))
                                .setSelectionFlags(C.SELECTION_FLAG_FORCED)
                                .build(),
                            C.TIME_UNSET
                        )
                val mergingMediaSource = MergingMediaSource(videoSource)
                exoPlayer.setMediaSource(mergingMediaSource)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                exoPlayer.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        if (playbackState == ExoPlayer.STATE_READY) {
                            mBinding.playerParent.setDuration(exoPlayer.duration)
                        }
                    }

                    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                        super.onPlayWhenReadyChanged(playWhenReady, reason)
                    }

                    override fun onTracksChanged(tracks: Tracks) {
                        super.onTracksChanged(tracks)
                        val trackGroups = tracks.groups
                        var selectIndex = -1
                        var selectGroupIndex = -1
                        trackGroups.forEachIndexed { groupIndex, trackGroup ->
                            when (trackGroup.type) {
                                C.TRACK_TYPE_TEXT -> {
                                    Log.d(TAG, "onTracksChanged: ${trackGroup.length}")
                                    for (trackIndex in 0 until trackGroup.length) {
                                        if (!trackGroup.isTrackSupported(trackIndex)) {
                                            continue
                                        }
                                        val trackFormat = trackGroup.getTrackFormat(trackIndex)
                                        if (trackFormat.selectionFlags and C.SELECTION_FLAG_FORCED != 0) {
                                            continue
                                        }
                                        val language = trackFormat.language
                                        val label = trackFormat.label
                                        if (language == "zh") {
                                            selectIndex = trackIndex
                                            selectGroupIndex = groupIndex
                                            if (label != null) {
                                                val count = arrayOf(
                                                    "Simplified",
                                                    "CHS"
                                                ).count { label.contains(it, true) }
                                                if (count > 0) {
                                                    selectIndex = trackIndex
                                                    selectGroupIndex = groupIndex
                                                }
                                            }
                                        }
                                    }
                                }

                                C.TRACK_TYPE_AUDIO -> {
                                    val trackFormat = trackGroup.getTrackSupport(0)
                                    val format = trackGroup.getTrackFormat(0)
                                    Log.d(
                                        TAG, "onTracksInfoChanged: ${
                                            Util.getPcmFormat(
                                                C.ENCODING_PCM_16BIT,
                                                format.channelCount,
                                                format.sampleRate
                                            )
                                        } ${format.cryptoType} ${trackGroup.length} ${format.sampleMimeType} $trackFormat $format"
                                    )
                                }
                            }
                        }

                        if (selectIndex != -1) {
                            val trackSelectionParameters =
                                player!!.trackSelectionParameters
                            player!!.trackSelectionParameters =
                                trackSelectionParameters
                                    .buildUpon()
                                    .setOverrideForType(
                                        TrackSelectionOverride(
                                            trackGroups[selectGroupIndex].mediaTrackGroup,
                                            selectIndex
                                        )
                                    )
                                    .setTrackTypeDisabled(
                                        C.TRACK_TYPE_TEXT,  /* disabled= */
                                        false
                                    )
                                    .build()
                        }
                    }
                })
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

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }
}