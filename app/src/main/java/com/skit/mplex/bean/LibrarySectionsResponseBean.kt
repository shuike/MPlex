package com.skit.mplex.bean
import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class LibrarySectionsResponseBean(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // false
        @SerializedName("Directory")
        val directory: List<Directory>,
        @SerializedName("size")
        val size: Long, // 1
        @SerializedName("title1")
        val title1: String // Plex Library
    ) {
        @Keep
        data class Directory(
            @SerializedName("agent")
            val agent: String, // com.plexapp.agents.none
            @SerializedName("allowSync")
            val allowSync: Boolean, // true
            @SerializedName("art")
            val art: String, // /:/resources/movie-fanart.jpg
            @SerializedName("composite")
            val composite: String, // /library/sections/1/composite/1681964442
            @SerializedName("content")
            val content: Boolean, // true
            @SerializedName("contentChangedAt")
            val contentChangedAt: Long, // 865
            @SerializedName("createdAt")
            val createdAt: Long, // 1681964441
            @SerializedName("directory")
            val directory: Boolean, // true
            @SerializedName("filters")
            val filters: Boolean, // true
            @SerializedName("hidden")
            val hidden: Int, // 0
            @SerializedName("key")
            val key: String, // 1
            @SerializedName("language")
            val language: String, // xn
            @SerializedName("Location")
            val location: List<Location>,
            @SerializedName("refreshing")
            val refreshing: Boolean, // false
            @SerializedName("scannedAt")
            val scannedAt: Long, // 1681964442
            @SerializedName("scanner")
            val scanner: String, // Plex Video Files Scanner
            @SerializedName("thumb")
            val thumb: String, // /:/resources/video.png
            @SerializedName("title")
            val title: String, // 其他影片
            @SerializedName("type")
            val type: String, // movie
            @SerializedName("updatedAt")
            val updatedAt: Long, // 1681964441
            @SerializedName("uuid")
            val uuid: String // c8ec5f7a-f219-4228-986d-0f61dbf3fc96
        ) {
            @Keep
            data class Location(
                @SerializedName("id")
                val id: Int, // 1
                @SerializedName("path")
                val path: String // /Users/shuike/投影仪共享/影院
            )
        }
    }
}
