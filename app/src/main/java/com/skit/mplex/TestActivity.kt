package com.skit.mplex

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.decoder.ffmpeg.FfmpegAudioRenderer
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.source.SingleSampleMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.TrackSelector
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import com.skit.mplex.databinding.ActivityTestBinding
import com.skit.mplex.ktx.dp
import java.io.File
import java.util.Locale

@OptIn(UnstableApi::class)

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTestBinding.inflate(layoutInflater)
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
//        val subtitleUri = Uri.parse("https://example.com/subtitles.srt")
//        val mediaItem: MediaItem = MediaItem.Builder()
//            .setUri(subtitleUri)
//            .build()
//        val build =
//            Cue.Builder().setText("asassdasdsa").build()
//        binding.subtitleView.setCues(listOf(build))

        val open = assets.open("srt.srt")
        val externalFilesDir = getExternalFilesDir("srt")!!
        externalFilesDir.exists()
        val file = File(externalFilesDir, "srt.srt")
        val outputStream = file.outputStream()
        open.copyTo(outputStream)
        outputStream.flush()
        outputStream.close()
        val uri = Uri.fromFile(file)
//        val file = FileUtils.writeTxtToFile(content, cacheDir.toString() + File.separator, txtName)
        val srtUri = uri // Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt")
//        val srtUri = Uri.fromFile(file)
// 在播放期间测量带宽。如果不需要，可以为空。
        val bandwidthMeter = DefaultBandwidthMeter.Builder(this).build()
        val trackSelector: TrackSelector =
            DefaultTrackSelector(this, AdaptiveTrackSelection.Factory())
        val exoPlayer = ExoPlayer.Builder(this, mRenderFactory)
            .setTrackSelector(trackSelector)
            .build()
// 生成用于加载媒体数据的数据源实例。
        val factory = DefaultDataSource.Factory(this, DefaultHttpDataSource.Factory().apply {
            setTransferListener(bandwidthMeter)
            setUserAgent(Util.getUserAgent(this@TestActivity, getString(R.string.app_name)))
        })
//        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
//            this,
//            Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter
//        )
        // 开发者准备好字幕资源。
//        val textFormat = Format.createTextSampleFormat(
//            null,
//            MimeTypes.APPLICATION_SUBRIP,
//            null,
//            Format.NO_VALUE,
//            Format.NO_VALUE,
//            Locale.getDefault().language,
//            null,
//            Format.OFFSET_SAMPLE_RELATIVE


        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)
        // 这是媒体资源，代表要播放的媒体。
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri("https://1-202-10-214.bf5071d7d9be4de7ac5a59315ca5c107.plex.direct:23996/library/parts/2527/1700387360/file.mkv?X-Plex-Token=YryXREeR9syxGYdwHFBs"))

        val configuration = MediaItem.SubtitleConfiguration.Builder(srtUri)
//            .setLanguage(Locale.getDefault().language)
            .build()
        val textMediaSource: MediaSource = SingleSampleMediaSource.Factory(factory)
            .createMediaSource(configuration, C.TIME_UNSET)
        exoPlayer.playWhenReady = true
        exoPlayer.setMediaSource(MergingMediaSource(videoSource, textMediaSource))
        binding.exoPlayerView.player = exoPlayer
        exoPlayer.prepare()
        exoPlayer.trackSelectionParameters = exoPlayer.trackSelectionParameters
            .buildUpon()
            .setPreferredTextLanguages("zh-CN", "zh-HK", "zh-TW")
            .setPreferredAudioLanguages("en-US")
            .build()
        binding.subtitleView.setCues(exoPlayer.currentCues.cues)
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

}