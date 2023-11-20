package com.skit.mplex

import android.util.Log
import androidx.media3.common.Format
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.TrackNameProvider
import java.util.Locale

@UnstableApi
class MTrackNameProvider : TrackNameProvider {
    companion object {
        private const val TAG = "MTrackNameProvider"
    }

    override fun getTrackName(format: Format): String {
        val language = format.language ?: return "未知"
        val locale = Locale.forLanguageTag(language)
        val suffix =
            format.label?.let { if (it.isEmpty() || it == "null") "" else " - $it" } ?: ""
        return locale.displayName + suffix
    }
}