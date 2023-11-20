package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class NewestResponse(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer,
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // true
        @SerializedName("art")
        val art: String, // /:/resources/show-fanart.jpg
        @SerializedName("identifier")
        val identifier: String, // com.plexapp.plugins.library
        @SerializedName("librarySectionID")
        val librarySectionID: Int, // 2
        @SerializedName("librarySectionTitle")
        val librarySectionTitle: String, // 电视节目
        @SerializedName("librarySectionUUID")
        val librarySectionUUID: String, // f49eb332-c014-4497-af1c-a08b821988d5
        @SerializedName("mediaTagPrefix")
        val mediaTagPrefix: String, // /system/bundle/media/flags/
        @SerializedName("mediaTagVersion")
        val mediaTagVersion: Long, // 1681751703
        @SerializedName("Metadata")
        val metadata: List<Metadata>,
        @SerializedName("mixedParents")
        val mixedParents: Boolean, // true
        @SerializedName("nocache")
        val nocache: Boolean, // true
        @SerializedName("size")
        val size: Long, // 70
        @SerializedName("thumb")
        val thumb: String, // /:/resources/show.png
        @SerializedName("title1")
        val title1: String, // 电视节目
        @SerializedName("title2")
        val title2: String, // Recently Released
        @SerializedName("viewGroup")
        val viewGroup: String, // episode
        @SerializedName("viewMode")
        val viewMode: Int, // 65592
    ) {
        @Keep
        data class Metadata(
            @SerializedName("addedAt")
            val addedAt: Long, // 1681756303
            @SerializedName("art")
            val art: String, // /library/metadata/376/art/1681966611
            @SerializedName("audienceRating")
            val audienceRating: Double, // 6.0
            @SerializedName("audienceRatingImage")
            val audienceRatingImage: String, // themoviedb://image.rating
            @SerializedName("contentRating")
            val contentRating: String, // TV-MA
            @SerializedName("Director")
            val director: List<Director>,
            @SerializedName("duration")
            val duration: Long, // 1298090
            @SerializedName("grandparentArt")
            val grandparentArt: String, // /library/metadata/376/art/1681966611
            @SerializedName("grandparentGuid")
            val grandparentGuid: String, // plex://show/5d9c086c7d06d9001ffd279e
            @SerializedName("grandparentKey")
            val grandparentKey: String, // /library/metadata/376
            @SerializedName("grandparentRatingKey")
            val grandparentRatingKey: String, // 376
            @SerializedName("grandparentTheme")
            val grandparentTheme: String, // /library/metadata/376/theme/1681966611
            @SerializedName("grandparentThumb")
            val grandparentThumb: String, // /library/metadata/376/thumb/1681966611
            @SerializedName("grandparentTitle")
            val grandparentTitle: String, // 恶搞之家
            @SerializedName("guid")
            val guid: String, // plex://episode/6171682b13d9ad5c2586dd4d
            @SerializedName("index")
            val index: Int, // 9
            @SerializedName("key")
            val key: String, // /library/metadata/778
            @SerializedName("Media")
            val media: List<Media>,
            @SerializedName("originalTitle")
            val originalTitle: String, // Family Guy
            @SerializedName("originallyAvailableAt")
            val originallyAvailableAt: String, // 2021-11-28
            @SerializedName("parentGuid")
            val parentGuid: String, // plex://season/6069297481d0ca002cf0d7f8
            @SerializedName("parentIndex")
            val parentIndex: Int, // 20
            @SerializedName("parentKey")
            val parentKey: String, // /library/metadata/769
            @SerializedName("parentRatingKey")
            val parentRatingKey: String, // 769
            @SerializedName("parentThumb")
            val parentThumb: String, // /library/metadata/769/thumb/1681966694
            @SerializedName("parentTitle")
            val parentTitle: String, // Season 20
            @SerializedName("ratingKey")
            val ratingKey: String, // 778
            @SerializedName("Role")
            val role: List<Role>,
            @SerializedName("summary")
            val summary: String, // In noir-style, Peter/Mac investigates the disappearance of Meg/Sister Megan.
            @SerializedName("thumb")
            val thumb: String, // /library/metadata/778/thumb/1681966704
            @SerializedName("title")
            val title: String, // The Fatman Always Rings Twice
            @SerializedName("titleSort")
            val titleSort: String, // Fatman Always Rings Twice
            @SerializedName("type")
            val type: String, // episode
            @SerializedName("updatedAt")
            val updatedAt: Long, // 1681966704
            @SerializedName("Writer")
            val writer: List<Writer>,
            @SerializedName("year")
            val year: Int, // 2021
        ) {
            @Keep
            data class Director(
                @SerializedName("tag")
                val tag: String, // Joe Vaux
            )

            @Keep
            data class Media(
                @SerializedName("aspectRatio")
                val aspectRatio: Double, // 1.78
                @SerializedName("audioChannels")
                val audioChannels: Int, // 2
                @SerializedName("audioCodec")
                val audioCodec: String, // aac
                @SerializedName("audioProfile")
                val audioProfile: String, // he-aac
                @SerializedName("bitrate")
                val bitrate: Int, // 915
                @SerializedName("container")
                val container: String, // mp4
                @SerializedName("duration")
                val duration: Long, // 1298090
                @SerializedName("has64bitOffsets")
                val has64bitOffsets: Boolean, // false
                @SerializedName("height")
                val height: Int, // 1080
                @SerializedName("id")
                val id: Int, // 757
                @SerializedName("optimizedForStreaming")
                val optimizedForStreaming: Int, // 1
                @SerializedName("Part")
                val part: List<Part>,
                @SerializedName("videoCodec")
                val videoCodec: String, // h264
                @SerializedName("videoFrameRate")
                val videoFrameRate: String, // 24p
                @SerializedName("videoProfile")
                val videoProfile: String, // high
                @SerializedName("videoResolution")
                val videoResolution: String, // 1080
                @SerializedName("width")
                val width: Int, // 1920
            ) {
                @Keep
                data class Part(
                    @SerializedName("audioProfile")
                    val audioProfile: String, // he-aac
                    @SerializedName("container")
                    val container: String, // mp4
                    @SerializedName("duration")
                    val duration: Long, // 1298090
                    @SerializedName("file")
                    val `file`: String, // /Users/shuike/投影仪共享/影院/Family guy (1999-)/Season 20/Family.Guy.S20E09.1080p.h264-通天塔字幕组.mp4
                    @SerializedName("has64bitOffsets")
                    val has64bitOffsets: Boolean, // false
                    @SerializedName("id")
                    val id: Int, // 757
                    @SerializedName("key")
                    val key: String, // /library/parts/757/1681756303/file.mp4
                    @SerializedName("optimizedForStreaming")
                    val optimizedForStreaming: Boolean, // true
                    @SerializedName("size")
                    val size: Long, // 148539873
                    @SerializedName("videoProfile")
                    val videoProfile: String, // high
                )
            }

            @Keep
            data class Role(
                @SerializedName("tag")
                val tag: String, // Danny Smith
            )

            @Keep
            data class Writer(
                @SerializedName("tag")
                val tag: String, // Alex Carter
            )
        }
    }
}