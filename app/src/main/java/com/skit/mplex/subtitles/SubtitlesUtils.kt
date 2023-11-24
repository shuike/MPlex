package com.skit.mplex.subtitles

import android.content.res.Resources
import android.net.Uri
import android.text.TextUtils
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.SingleSampleMediaSource
import androidx.media3.ui.R
import com.skit.mplex.MPlexApp
import java.io.File
import java.util.Locale


@OptIn(UnstableApi::class)
object SubtitlesUtils {
    private fun application() = MPlexApp.application
    val resources: Resources get() = application().resources

    private fun getLocalSubtitlesUrl(path: String): Uri? {
        val file = File(path)
        if (!file.exists()) {
            return null
        }
        return Uri.fromFile(file)
    }

    fun getTextMediaSource(
        id: String,
        subTitleUri: Uri,
        codec: String,
        language: String,
        factory: DataSource.Factory = DefaultHttpDataSource.Factory()
    ): MediaSource {
        val mimeType = when (codec) {
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
        val context = application()
        val textFormat =
            Format.Builder() /// 其他的比如 text/x-ssa ，text/vtt，application/ttml+xml 等等
                .setSampleMimeType(mimeType)
                .setSelectionFlags(C.SELECTION_FLAG_FORCED)
                /// 如果出现字幕不显示，可以通过修改这个语音去对应，
                //  这个问题在内部的 selectTextTrack 时，TextTrackScore 通过 getFormatLanguageScore 方法判断语言获取匹配不上
                //  就会不出现字幕
                .setLanguage(language)
                .build()
        val subtitle = MediaItem.SubtitleConfiguration.Builder(subTitleUri)
            .setMimeType(checkNotNull(textFormat.sampleMimeType))
            .setId(id)
            .setMimeType(mimeType)
            .setLanguage(textFormat.language)
            .setSelectionFlags(textFormat.selectionFlags).build()
        return SingleSampleMediaSource.Factory(
            DefaultDataSource.Factory(
                context,
                factory
            )
        ).createMediaSource(subtitle, C.TIME_UNSET)
    }


    fun getName(format: Format): String {
        val languageAndRole: String =
            joinWithSeparator(buildLanguageString(format), buildRoleString(format))
        return if (TextUtils.isEmpty(languageAndRole)) buildLabelString(format) else languageAndRole
    }

    // from media3
    private fun joinWithSeparator(vararg items: String): String {
        var itemList = ""
        for (item in items) {
            if (item.isNotEmpty()) {
                itemList = if (TextUtils.isEmpty(itemList)) {
                    item
                } else {
                    resources.getString(androidx.media3.ui.R.string.exo_item_list, itemList, item)
                }
            }
        }
        return itemList
    }

    private fun buildRoleString(format: Format): String {
        var roles: String? = ""
        if (format.roleFlags and C.ROLE_FLAG_ALTERNATE != 0) {
            roles = resources.getString(androidx.media3.ui.R.string.exo_track_role_alternate)
        }
        if (format.roleFlags and C.ROLE_FLAG_SUPPLEMENTARY != 0) {
            roles = joinWithSeparator(
                roles!!,
                resources.getString(R.string.exo_track_role_supplementary)
            )
        }
        if (format.roleFlags and C.ROLE_FLAG_COMMENTARY != 0) {
            roles =
                joinWithSeparator(
                    roles!!,
                    resources.getString(R.string.exo_track_role_commentary)
                )
        }
        if (format.roleFlags and (C.ROLE_FLAG_CAPTION or C.ROLE_FLAG_DESCRIBES_MUSIC_AND_SOUND) != 0) {
            roles = joinWithSeparator(
                roles!!,
                resources.getString(R.string.exo_track_role_closed_captions)
            )
        }
        return roles ?: ""
    }

    private fun buildLabelString(format: Format): String {
        return if (TextUtils.isEmpty(format.label)) "" else format.label!!
    }


    private fun buildLanguageString(format: Format): String {
        val language = format.language
        if (TextUtils.isEmpty(language) || C.LANGUAGE_UNDETERMINED == language) {
            return ""
        }
        val languageLocale =
            if (Util.SDK_INT >= 21) Locale.forLanguageTag(language) else Locale(language)
        val displayLocale = Util.getDefaultDisplayLocale()
        val languageName = languageLocale.getDisplayName(displayLocale)
        return if (TextUtils.isEmpty(languageName)) {
            ""
        } else try {
            // Capitalize the first letter. See: https://github.com/google/ExoPlayer/issues/9452.
            val firstCodePointLength = languageName.offsetByCodePoints(0, 1)
            (languageName.substring(0, firstCodePointLength).uppercase(displayLocale)
                    + languageName.substring(firstCodePointLength))
        } catch (e: IndexOutOfBoundsException) {
            // Should never happen, but return the unmodified language name if it does.
            languageName
        }
    }
}