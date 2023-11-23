package com.skit.mplex

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.SubtitleConfiguration
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
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
import androidx.media3.exoplayer.source.SingleSampleMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import com.skit.mplex.databinding.ActivityTestBinding
import com.skit.mplex.ktx.dp
import com.skit.mplex.net.HttpFactory
import com.skit.mplex.net.plexUrl
import com.skit.mplex.net.plexUrlAddToken
import com.skit.mplex.server.PlexLocalApi
import com.skit.mplex.utils.SubsUtils
import kotlinx.coroutines.launch
import java.io.File
import java.util.LinkedList


@OptIn(UnstableApi::class)

class TestActivity : AppCompatActivity() {
    private val TAG = "TestActivity"
    private lateinit var dataSourceFactory: DataSource.Factory
    private lateinit var httpDataSource: DefaultHttpDataSource
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var binding: ActivityTestBinding
    private lateinit var trackSelector: DefaultTrackSelector
    private val subtitles = LinkedList<Triple<String, String, String>>()

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
            val tvShowChildMetaData = plexLocalApi.getTvShowChildMetaData("682")
            val metadata = tvShowChildMetaData.mediaContainer.metadata[0]
            val media = metadata.media[0]
            val part = media.part[0]
            val stream = part.stream
            stream.filter { it.streamType == 3 }.forEach {
                val streamType = it.streamType // 1:视频 2:音频 3:字幕
                if (it.key != null) {
                    subtitles.add(Triple(it.codec, it.languageTag, it.key.plexUrlAddToken()))
                }
//                binding.text.append("${it.id} ${it.codec} ${it.default} ${it.selected} ${it.languageTag} ${it.displayTitle} ${it.key}\n\n")
            }
            val plexUrl = part.key.plexUrl()
            play(plexUrl)
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
//        val defaultExtractorsFactory = DefaultExtractorsFactory()
        val videoSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoPath))
        val map = subtitles.mapIndexed { index, triple ->
            getTextSource("r_${index}", Uri.parse(triple.third), triple.first, triple.second)
        }.toTypedArray()
        val mergingMediaSource = MergingMediaSource(videoSource, *map)
        exoPlayer.setMediaSource(mergingMediaSource)
//        MergingMediaSource(
//            videoSource,
//            //                getTextSource(getStrUrl("srt2.srt"), "zh-CN"),
//            getTextSource(getStrUrl("srt2.srt"), "srt", "zh-CN"),
//            //                getTextSource(getStrUrl("srt2.srt"), "zh"),
//            //                getTextSource(getStrUrl("srt2.srt"), "en"),
//            //                getTextSource(getStrUrl("srt2.srt"), "en-US"),
//            getTextSource(getStrUrl("ass.ass"), "ass", "zh-CN"),
//        )

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

    private val playerSubTitles = mutableListOf<Tracks.Group>()
    fun prepareSubTitles(exoPlayer: ExoPlayer) {
        playerSubTitles.clear()

        binding.text.text = ""
        val currentTracks = exoPlayer.currentTracks
        val groups = currentTracks.groups
        val groupList = mutableListOf<Tracks.Group>()
        groups.forEachIndexed { index, it ->
            if (it.type == C.TRACK_TYPE_TEXT) { // 获取字幕轨道
                groupList.add(it)
                playerSubTitles.add(it)
            }
        }
        groupList.forEach {
            val mediaTrackGroup = it.mediaTrackGroup
            val format = mediaTrackGroup.getFormat(0)
            val title = SubsUtils.getName(format)
            binding.text.append(
                title + " ${format.id} " + format.toString() + " ${mediaTrackGroup.length} \n"
            )
            if (format.id == "r_0") {
                trackSelector.setParameters(
                    trackSelector.parameters.buildUpon()
                        .setPreferredTextLanguage(format.language)
                        .setOverrideForType(
                            TrackSelectionOverride(
                                mediaTrackGroup, 0
                            )
                        )
                        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, /* disabled= */ false)
                )
            }
        }
    }

    private fun getStrUrl(s: String): Uri {
        val open = assets.open(s)
        val externalFilesDir = filesDir
        //        externalFilesDir.exists()
        val file = File(externalFilesDir, s)
        val outputStream = file.outputStream()
        open.copyTo(outputStream)
        outputStream.flush()
        outputStream.close()
        val srtUrl = Uri.fromFile(file)
        return srtUrl
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

    private fun getTextSource(
        id: String,
        subTitleUri: Uri,
        typeStr: String,
        language: String,
        factory: DataSource.Factory = DefaultHttpDataSource.Factory()
    ): MediaSource {
        Log.d("TAG", "getTextSource: ${subTitleUri}")
        val str = subTitleUri.toString()
        val type = when (typeStr) {
            "srt" -> {
                MimeTypes.APPLICATION_SUBRIP
            }

            "ass" -> {
                MimeTypes.TEXT_SSA
            }

            "ttml" -> {
                MimeTypes.APPLICATION_TTML
            }

            else -> {
                MimeTypes.TEXT_UNKNOWN
            }
        }
        val context = this
        val textFormat =
            Format.Builder() /// 其他的比如 text/x-ssa ，text/vtt，application/ttml+xml 等等
                .setSampleMimeType(type)
                .setSelectionFlags(C.SELECTION_FLAG_FORCED)
                /// 如果出现字幕不显示，可以通过修改这个语音去对应，
                //  这个问题在内部的 selectTextTrack 时，TextTrackScore 通过 getFormatLanguageScore 方法判断语言获取匹配不上
                //  就会不出现字幕
                .setLanguage(language)
                .build()
        val subtitle = SubtitleConfiguration.Builder(subTitleUri)
            .setMimeType(checkNotNull(textFormat.sampleMimeType))
            .setId(id)
            .setMimeType(type)
            .setLanguage(textFormat.language)
            .setSelectionFlags(textFormat.selectionFlags).build()
//        val factory = DefaultHttpDataSource.Factory()
//            .setAllowCrossProtocolRedirects(true)
//            .setConnectTimeoutMs(50000)
//            .setReadTimeoutMs(50000)
//            .setTransferListener(DefaultBandwidthMeter.Builder(context).build())
        return SingleSampleMediaSource.Factory(
            DefaultDataSource.Factory(
                context,
                factory
            )
        ).createMediaSource(subtitle, C.TIME_UNSET)
    }
}