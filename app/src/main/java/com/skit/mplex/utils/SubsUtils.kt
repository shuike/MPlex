package com.skit.mplex.utils

import android.content.res.Resources
import android.text.TextUtils
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.util.Util
import com.skit.mplex.MPlexApp
import java.util.Locale

object SubsUtils {
    val resources: Resources get() = MPlexApp.application.resources

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
                resources.getString(androidx.media3.ui.R.string.exo_track_role_supplementary)
            )
        }
        if (format.roleFlags and C.ROLE_FLAG_COMMENTARY != 0) {
            roles =
                joinWithSeparator(
                    roles!!,
                    resources.getString(androidx.media3.ui.R.string.exo_track_role_commentary)
                )
        }
        if (format.roleFlags and (C.ROLE_FLAG_CAPTION or C.ROLE_FLAG_DESCRIBES_MUSIC_AND_SOUND) != 0) {
            roles = joinWithSeparator(
                roles!!,
                resources.getString(androidx.media3.ui.R.string.exo_track_role_closed_captions)
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