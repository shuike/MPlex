package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class ContinueWatchingResponse(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer,
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // true
        @SerializedName("Hub")
        val hub: List<Hub>,
        @SerializedName("identifier")
        val identifier: String, // com.plexapp.plugins.library
        @SerializedName("size")
        val size: Long, // 1
    ) {
        @Keep
        data class Hub(
            @SerializedName("context")
            val context: String, // hub.continueWatching
            @SerializedName("hubIdentifier")
            val hubIdentifier: String, // continueWatching
            @SerializedName("hubKey")
            val hubKey: String, // /library/metadata/378
            @SerializedName("key")
            val key: String, // /hubs/continueWatching/items
            @SerializedName("Metadata")
            val metadata: List<Metadata>,
            @SerializedName("more")
            val more: Boolean, // false
            @SerializedName("size")
            val size: Long, // 1
            @SerializedName("style")
            val style: String, // hero
            @SerializedName("title")
            val title: String, // 继续观看
            @SerializedName("type")
            val type: String, // mixed
        ) {
            @Keep
            data class Metadata(
                @SerializedName("addedAt")
                val addedAt: Long, // 1681753571
                @SerializedName("art")
                val art: String, // /library/metadata/376/art/1681966611
                @SerializedName("audienceRating")
                val audienceRating: Double, // 7.2
                @SerializedName("audienceRatingImage")
                val audienceRatingImage: String, // themoviedb://image.rating
                @SerializedName("contentRating")
                val contentRating: String, // TV-MA
                @SerializedName("Director")
                val director: List<Director>,
                @SerializedName("duration")
                val duration: Long, // 1348613
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
                val guid: String, // plex://episode/5d9c127d08fddd001f316cd6
                @SerializedName("includedAt")
                val includedAt: Long, // 1681971776
                @SerializedName("index")
                val index: Int, // 2
                @SerializedName("key")
                val key: String, // /library/metadata/378
                @SerializedName("lastViewedAt")
                val lastViewedAt: Long, // 1681971776
                @SerializedName("librarySectionID")
                val librarySectionID: Int, // 2
                @SerializedName("librarySectionKey")
                val librarySectionKey: String, // /library/sections/2
                @SerializedName("librarySectionTitle")
                val librarySectionTitle: String, // 电视节目
                @SerializedName("Media")
                val media: List<Media>,
                @SerializedName("originalTitle")
                val originalTitle: String, // Family Guy
                @SerializedName("originallyAvailableAt")
                val originallyAvailableAt: String, // 1999-04-11
                @SerializedName("parentIndex")
                val parentIndex: Int, // 1
                @SerializedName("parentKey")
                val parentKey: String, // /library/metadata/377
                @SerializedName("parentRatingKey")
                val parentRatingKey: String, // 377
                @SerializedName("parentTitle")
                val parentTitle: String, // 季 1
                @SerializedName("ratingKey")
                val ratingKey: String, // 378
                @SerializedName("Role")
                val role: List<Role>,
                @SerializedName("thumb")
                val thumb: String, // /library/metadata/378/thumb/1681966613
                @SerializedName("title")
                val title: String, // 我从未见过死者
                @SerializedName("type")
                val type: String, // episode
                @SerializedName("updatedAt")
                val updatedAt: Long, // 1681966613
                @SerializedName("viewOffset")
                val viewOffset: Long, // 410000
                @SerializedName("Writer")
                val writer: List<Writer>,
                @SerializedName("year")
                val year: Int, // 1999
            ) {
                @Keep
                data class Director(
                    @SerializedName("tag")
                    val tag: String, // Michael Dante DiMartino
                )

                @Keep
                data class Media(
                    @SerializedName("aspectRatio")
                    val aspectRatio: Double, // 1.33
                    @SerializedName("audioChannels")
                    val audioChannels: Int, // 2
                    @SerializedName("audioCodec")
                    val audioCodec: String, // aac
                    @SerializedName("audioProfile")
                    val audioProfile: String, // lc
                    @SerializedName("bitrate")
                    val bitrate: Int, // 686
                    @SerializedName("container")
                    val container: String, // mp4
                    @SerializedName("duration")
                    val duration: Long, // 1348613
                    @SerializedName("has64bitOffsets")
                    val has64bitOffsets: Boolean, // false
                    @SerializedName("height")
                    val height: Int, // 480
                    @SerializedName("id")
                    val id: Int, // 376
                    @SerializedName("optimizedForStreaming")
                    val optimizedForStreaming: Int, // 1
                    @SerializedName("Part")
                    val part: List<Part>,
                    @SerializedName("videoCodec")
                    val videoCodec: String, // h264
                    @SerializedName("videoFrameRate")
                    val videoFrameRate: String, // PAL
                    @SerializedName("videoProfile")
                    val videoProfile: String, // high
                    @SerializedName("videoResolution")
                    val videoResolution: String, // 480
                    @SerializedName("width")
                    val width: Int, // 640
                ) {
                    @Keep
                    data class Part(
                        @SerializedName("audioProfile")
                        val audioProfile: String, // lc
                        @SerializedName("container")
                        val container: String, // mp4
                        @SerializedName("duration")
                        val duration: Long, // 1348613
                        @SerializedName("file")
                        val `file`: String, // /Users/shuike/投影仪共享/影院/Family guy (1999-)/Season 01/Family.Guy.S01E02.I.Never.Met.the.Dead.Man.SDTV.mp4
                        @SerializedName("has64bitOffsets")
                        val has64bitOffsets: Boolean, // false
                        @SerializedName("id")
                        val id: Int, // 376
                        @SerializedName("key")
                        val key: String, // /library/parts/376/1681753571/file.mp4
                        @SerializedName("optimizedForStreaming")
                        val optimizedForStreaming: Boolean, // true
                        @SerializedName("size")
                        val size: Long, // 115569845
                        @SerializedName("videoProfile")
                        val videoProfile: String, // high
                    )
                }

                @Keep
                data class Role(
                    @SerializedName("tag")
                    val tag: String, // Aaron Lustig
                )

                @Keep
                data class Writer(
                    @SerializedName("tag")
                    val tag: String, // Chris Sheridan
                )
            }
        }
    }
}