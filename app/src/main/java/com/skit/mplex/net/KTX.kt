package com.skit.mplex.net

import android.net.Uri

fun String.plexUrlAddToken(): String {
    val host = if (this.startsWith(HttpFactory.HOST)) {
        ""
    } else {
        HttpFactory.HOST
    }
    val uri = Uri.Builder()
        .encodedPath(host + this)
        .appendQueryParameter("X-Plex-Token", HttpFactory.PLEX_TOKEN)
        .build()
    return uri.toString()
}

fun String.plexUrl(): String {
    val host = if (this.startsWith(HttpFactory.HOST)) {
        ""
    } else {
        HttpFactory.HOST
    }
    val uri = Uri.Builder()
        .encodedPath(host + this)
        .build()
    return uri.toString()
}