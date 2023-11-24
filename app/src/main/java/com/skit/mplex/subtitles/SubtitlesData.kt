package com.skit.mplex.subtitles

sealed class SubtitlesData() {
    data class Remote(
        val id: String,
        val codec: String,
        val language: String,
        val name: String,
        val url: String,
        val selected: Boolean = false
    ) : SubtitlesData()

    data class Inner(
        val id: String,
        val language: String,
        val name: String
    ) : SubtitlesData()
}