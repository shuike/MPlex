package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class TvShowSeasonChildrenResponse(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer,
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // true
        @SerializedName("art")
        val art: String, // /library/metadata/1312/art/1683056998
        @SerializedName("grandparentContentRating")
        val grandparentContentRating: String, // TV-14
        @SerializedName("grandparentRatingKey")
        val grandparentRatingKey: Int, // 1312
        @SerializedName("grandparentStudio")
        val grandparentStudio: String, // Team Downey
        @SerializedName("grandparentTheme")
        val grandparentTheme: String, // /library/metadata/1312/theme/1683056998
        @SerializedName("grandparentThumb")
        val grandparentThumb: String, // /library/metadata/1312/thumb/1683056998
        @SerializedName("grandparentTitle")
        val grandparentTitle: String, // 鹿角男孩
        @SerializedName("identifier")
        val identifier: String, // com.plexapp.plugins.library
        @SerializedName("key")
        val key: String, // 1313
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
        val parentTitle: String,
        @SerializedName("size")
        val size: Int, // 8
        @SerializedName("theme")
        val theme: String, // /library/metadata/1312/theme/1683056998
        @SerializedName("thumb")
        val thumb: String, // /library/metadata/1313/thumb/1682919939
        @SerializedName("title1")
        val title1: String, // 鹿角男孩
        @SerializedName("title2")
        val title2: String, // Season 1
        @SerializedName("totalSize")
        val totalSize: Int, // 8
        @SerializedName("viewGroup")
        val viewGroup: String, // episode
        @SerializedName("viewMode")
        val viewMode: Int, // 65592
    ) {
        @Keep
        data class Metadata(
            @SerializedName("addedAt")
            val addedAt: Long, // 1682919926
            @SerializedName("art")
            val art: String, // /library/metadata/1312/art/1683056998
            @SerializedName("audienceRating")
            val audienceRating: Double, // 6.8
            @SerializedName("audienceRatingImage")
            val audienceRatingImage: String, // themoviedb://image.rating
            @SerializedName("contentRating")
            val contentRating: String, // TV-14
            @SerializedName("Director")
            val director: List<Director>,
            @SerializedName("duration")
            val duration: Long, // 3284000
            @SerializedName("grandparentArt")
            val grandparentArt: String, // /library/metadata/1312/art/1683056998
            @SerializedName("grandparentGuid")
            val grandparentGuid: String, // plex://show/5ecc3ba48e44b0004171c15e
            @SerializedName("grandparentKey")
            val grandparentKey: String, // /library/metadata/1312
            @SerializedName("grandparentRatingKey")
            val grandparentRatingKey: String, // 1312
            @SerializedName("grandparentTheme")
            val grandparentTheme: String, // /library/metadata/1312/theme/1683056998
            @SerializedName("grandparentThumb")
            val grandparentThumb: String, // /library/metadata/1312/thumb/1683056998
            @SerializedName("grandparentTitle")
            val grandparentTitle: String, // 鹿角男孩
            @SerializedName("guid")
            val guid: String, // plex://episode/5ecc3ba58e44b0004171c198
            @SerializedName("index")
            val index: Int, // 1
            @SerializedName("key")
            val key: String, // /library/metadata/1314
            @SerializedName("lastViewedAt")
            val lastViewedAt: Long, // 1682920044
            @SerializedName("Media")
            val media: List<Media>,
            @SerializedName("originalTitle")
            val originalTitle: String, // Sweet Tooth
            @SerializedName("originallyAvailableAt")
            val originallyAvailableAt: String, // 2021-06-04
            @SerializedName("parentGuid")
            val parentGuid: String, // plex://season/602e7ac60f4bde002da532ae
            @SerializedName("parentIndex")
            val parentIndex: Int, // 1
            @SerializedName("parentKey")
            val parentKey: String, // /library/metadata/1313
            @SerializedName("parentRatingKey")
            val parentRatingKey: String, // 1313
            @SerializedName("parentThumb")
            val parentThumb: String, // /library/metadata/1313/thumb/1682919939
            @SerializedName("parentTitle")
            val parentTitle: String, // 季 1
            @SerializedName("ratingKey")
            val ratingKey: String, // 1314
            @SerializedName("Role")
            val role: List<Role>,
            @SerializedName("summary")
            val summary: String, // 盖斯跟随父亲在一个偏远的森林小木屋中长大，他学会了如何生存，并了解了铁丝网外面的世界潜伏着的危险。
            @SerializedName("thumb")
            val thumb: String, // /library/metadata/1314/thumb/1682919939
            @SerializedName("title")
            val title: String, // 走出森林
            @SerializedName("type")
            val type: String, // episode
            @SerializedName("updatedAt")
            val updatedAt: Long, // 1682919939
            @SerializedName("Writer")
            val writer: List<Writer>,
            @SerializedName("year")
            val year: Int, // 2021
        ) {
            @Keep
            data class Director(
                @SerializedName("tag")
                val tag: String, // Jim Mickle
            )

            @Keep
            data class Media(
                @SerializedName("aspectRatio")
                val aspectRatio: Double, // 1.78
                @SerializedName("audioChannels")
                val audioChannels: Int, // 6
                @SerializedName("audioCodec")
                val audioCodec: String, // eac3
                @SerializedName("bitrate")
                val bitrate: Int, // 12061
                @SerializedName("container")
                val container: String, // mkv
                @SerializedName("duration")
                val duration: Long, // 3284000
                @SerializedName("height")
                val height: Int, // 2160
                @SerializedName("id")
                val id: Int, // 1828
                @SerializedName("Part")
                val part: List<Part>,
                @SerializedName("videoCodec")
                val videoCodec: String, // hevc
                @SerializedName("videoFrameRate")
                val videoFrameRate: String, // 24p
                @SerializedName("videoProfile")
                val videoProfile: String, // main 10
                @SerializedName("videoResolution")
                val videoResolution: String, // 4k
                @SerializedName("width")
                val width: Int, // 3840
            ) {
                @Keep
                data class Part(
                    @SerializedName("container")
                    val container: String, // mkv
                    @SerializedName("duration")
                    val duration: Long, // 3284000
                    @SerializedName("file")
                    val `file`: String, // /share/CACHEDEV1_DATA/家庭影院/Video/电视剧/鹿角男孩 (2021-)/Season 1/sweet.tooth.s01e01.2160p.web.h265-donuts.mkv
                    @SerializedName("id")
                    val id: Int, // 1828
                    @SerializedName("key")
                    val key: String, // /library/parts/1828/1682751981/file.mkv
                    @SerializedName("size")
                    val size: Long, // 4952391319
                    @SerializedName("videoProfile")
                    val videoProfile: String, // main 10
                )
            }

            @Keep
            data class Role(
                @SerializedName("tag")
                val tag: String, // Nixon Bingley
            )

            @Keep
            data class Writer(
                @SerializedName("tag")
                val tag: String, // Jim Mickle
            )
        }
    }
}