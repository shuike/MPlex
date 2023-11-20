package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class TvShowChildrenResponse(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer,
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // true
        @SerializedName("art")
        val art: String, // /library/metadata/1312/art/1683056998
        @SerializedName("identifier")
        val identifier: String, // com.plexapp.plugins.library
        @SerializedName("key")
        val key: String, // 1312
        @SerializedName("librarySectionID")
        val librarySectionID: Int, // 3
        @SerializedName("librarySectionTitle")
        val librarySectionTitle: String, // 电视剧
        @SerializedName("librarySectionUUID")
        val librarySectionUUID: String, // 727da56b-8818-47ca-b641-f8e8a2a677b5
        @SerializedName("mediaTagPrefix")
        val mediaTagPrefix: String, // /system/bundle/media/flags/
        @SerializedName("mediaTagVersion")
        val mediaTagVersion: Long, // 1682023669
        @SerializedName("Metadata")
        val metadata: List<Metadata>,
        @SerializedName("nocache")
        val nocache: Boolean, // true
        @SerializedName("offset")
        val offset: Int, // 0
        @SerializedName("parentIndex")
        val parentIndex: Int, // 1
        @SerializedName("parentTitle")
        val parentTitle: String, // 鹿角男孩
        @SerializedName("parentYear")
        val parentYear: Int, // 2021
        @SerializedName("size")
        val size: Int, // 2
        @SerializedName("summary")
        val summary: String, // 为了寻找家人和家园，半人半鹿的可爱男孩与他暴躁的守护者踏上了后末日世界的传奇冒险之旅。
        @SerializedName("theme")
        val theme: String, // /library/metadata/1312/theme/1683056998
        @SerializedName("thumb")
        val thumb: String, // /library/metadata/1312/thumb/1683056998
        @SerializedName("title1")
        val title1: String, // 电视剧
        @SerializedName("title2")
        val title2: String, // 鹿角男孩
        @SerializedName("totalSize")
        val totalSize: Int, // 2
        @SerializedName("viewGroup")
        val viewGroup: String, // season
        @SerializedName("viewMode")
        val viewMode: Int, // 65593
    ) {
        @Keep
        data class Metadata(
            @SerializedName("addedAt")
            val addedAt: Long, // 1682919926
            @SerializedName("art")
            val art: String, // /library/metadata/1312/art/1683056998
            @SerializedName("guid")
            val guid: String, // plex://season/602e7ac60f4bde002da532ae
            @SerializedName("index")
            val index: Int, // 1
            @SerializedName("key")
            val key: String, // /library/metadata/1313/children
            @SerializedName("lastViewedAt")
            val lastViewedAt: Long, // 1682920044
            @SerializedName("leafCount")
            val leafCount: Int, // 8
            @SerializedName("parentGuid")
            val parentGuid: String, // plex://show/5ecc3ba48e44b0004171c15e
            @SerializedName("parentIndex")
            val parentIndex: Int, // 1
            @SerializedName("parentKey")
            val parentKey: String, // /library/metadata/1312
            @SerializedName("parentRatingKey")
            val parentRatingKey: String, // 1312
            @SerializedName("parentStudio")
            val parentStudio: String, // Team Downey
            @SerializedName("parentTheme")
            val parentTheme: String, // /library/metadata/1312/theme/1683056998
            @SerializedName("parentThumb")
            val parentThumb: String, // /library/metadata/1312/thumb/1683056998
            @SerializedName("parentTitle")
            val parentTitle: String, // 鹿角男孩
            @SerializedName("parentYear")
            val parentYear: Int, // 2021
            @SerializedName("ratingKey")
            val ratingKey: String, // 1313
            @SerializedName("summary")
            val summary: String,
            @SerializedName("thumb")
            val thumb: String, // /library/metadata/1313/thumb/1682919939
            @SerializedName("title")
            val title: String, // 季 1
            @SerializedName("type")
            val type: String, // season
            @SerializedName("updatedAt")
            val updatedAt: Long, // 1682919939
            @SerializedName("viewedLeafCount")
            val viewedLeafCount: Int, // 0
        )
    }
}