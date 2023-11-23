package com.skit.mplex.ui.play

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
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
import androidx.media3.exoplayer.trackselection.TrackSelector
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.R
import com.skit.mplex.MTrackNameProvider
import com.skit.mplex.databinding.ActivityPlayerBinding
import com.skit.mplex.ktx.launch
import com.skit.mplex.net.HttpFactory
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
        val httpDataSource = httpDataSource(this).apply {
            setRequestProperty("X-Plex-Token", HttpFactory.PLEX_TOKEN)
        }
        val dataSourceFactory = DataSource.Factory { httpDataSource }

        val trackSelector: TrackSelector =
            DefaultTrackSelector(this, AdaptiveTrackSelection.Factory())
        player = ExoPlayer.Builder(this)
            .setRenderersFactory(mRenderFactory)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(this).setDataSourceFactory(dataSourceFactory)
            )
            .setTrackSelector(trackSelector)
            .build()
            .also { exoPlayer ->
                mBinding.exoPlayerView.player = exoPlayer
                mBinding.exoPlayerView.controllerAutoShow = false
                mBinding.exoPlayerView.setErrorMessageProvider {
                    val exoPlaybackException = it as ExoPlaybackException
                    it.printStackTrace()
                    Pair(0, "${exoPlaybackException.errorCode}")
                }
                val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(path))
                val mergingMediaSource = MergingMediaSource(videoSource)
                exoPlayer.setMediaSource(mergingMediaSource)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true

                val trackSelectionParameters = exoPlayer.trackSelectionParameters
                exoPlayer.trackSelectionParameters = trackSelectionParameters
                    .buildUpon()
                    .setPreferredTextLanguages("zh-CN", "zh-HK", "zh-TW")
                    .setPreferredAudioLanguages("en-US")
                    .build()
                exoPlayer.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        super.onPlaybackStateChanged(playbackState)
                        if (playbackState == ExoPlayer.STATE_READY) {
                            mBinding.playerParent.setDuration(exoPlayer.duration)
                        }
                    }
                })
            }
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