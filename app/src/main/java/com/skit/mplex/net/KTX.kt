package com.skit.mplex.net

import android.net.Uri

fun String.plexUrl(): String {
    val uri = Uri.Builder()
        .encodedPath(HttpFactory.HOST + this)
        .appendQueryParameter("X-Plex-Token", HttpFactory.PLEX_TOKEN)
        .build()
    return uri.toString()
}