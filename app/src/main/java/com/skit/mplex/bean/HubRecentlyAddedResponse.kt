package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName

@Keep
data class HubRecentlyAddedResponse(
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
            val context: String, // hub.tv.recentlyadded
            @SerializedName("hubIdentifier")
            val hubIdentifier: String, // tv.recentlyadded.2
            @SerializedName("hubKey")
            val hubKey: String, // /library/metadata/782,376
            @SerializedName("key")
            val key: String, // /hubs/home/recentlyAdded?type=2&sectionID=2
            @SerializedName("Metadata")
            val metadata: List<Metadata>,
            @SerializedName("more")
            val more: Boolean, // false
            @SerializedName("promoted")
            val promoted: Boolean, // true
            @SerializedName("size")
            val size: Long, // 2
            @SerializedName("style")
            val style: String, // shelf
            @SerializedName("title")
            val title: String, // 最近添加的电视节目
            @SerializedName("type")
            val type: String, // mixed
        ) {
            @Keep
            data class Metadata(
                @SerializedName("addedAt")
                val addedAt: Long, // 1681973924
                @SerializedName("art")
                val art: String?, // /library/metadata/780/art/1681973929
                @SerializedName("audienceRating")
                val audienceRating: Double, // 8.1
                @SerializedName("audienceRatingImage")
                val audienceRatingImage: String, // themoviedb://image.rating
                @SerializedName("childCount")
                val childCount: Int, // 20
                @SerializedName("contentRating")
                val contentRating: String, // TV-MA
                @SerializedName("Country")
                val country: List<Country>,
                @SerializedName("Director")
                val director: List<Director>,
                @SerializedName("duration")
                val duration: Long, // 1348613
                @SerializedName("Genre")
                val genre: List<Genre>,
                @SerializedName("grandparentArt")
                val grandparentArt: String, // /library/metadata/780/art/1681973929
                @SerializedName("grandparentGuid")
                val grandparentGuid: String, // plex://show/5d9c08382192ba001f30a9c7
                @SerializedName("grandparentKey")
                val grandparentKey: String, // /library/metadata/780
                @SerializedName("grandparentRatingKey")
                val grandparentRatingKey: String, // 780
                @SerializedName("grandparentTheme")
                val grandparentTheme: String, // /library/metadata/780/theme/1681973929
                @SerializedName("grandparentThumb")
                val grandparentThumb: String, // /library/metadata/780/thumb/1681973929
                @SerializedName("grandparentTitle")
                val grandparentTitle: String, // 瑞克和莫蒂
                @SerializedName("guid")
                val guid: String, // plex://episode/5d9c1004705e7a001e73f2e7
                @SerializedName("Guid")
                val guids: List<Guid>,
                @SerializedName("hasPremiumExtras")
                val hasPremiumExtras: String, // 1
                @SerializedName("hasPremiumPrimaryExtra")
                val hasPremiumPrimaryExtra: String, // 1
                @SerializedName("index")
                val index: Int, // 2
                @SerializedName("key")
                val key: String, // /library/metadata/782
                @SerializedName("lastViewedAt")
                val lastViewedAt: Long, // 1681971776
                @SerializedName("leafCount")
                val leafCount: Int, // 376
                @SerializedName("librarySectionID")
                val librarySectionID: Int, // 2
                @SerializedName("librarySectionKey")
                val librarySectionKey: String, // /library/sections/2
                @SerializedName("librarySectionTitle")
                val librarySectionTitle: String, // 电视节目
                @SerializedName("Location")
                val location: List<Location>,
                @SerializedName("Media")
                val media: List<Media>?,
                @SerializedName("originalTitle")
                val originalTitle: String, // Rick and Morty
                @SerializedName("originallyAvailableAt")
                val originallyAvailableAt: String, // 2013-12-09
                @SerializedName("parentGuid")
                val parentGuid: String, // plex://season/602e626fc96042002d07b09e
                @SerializedName("parentIndex")
                val parentIndex: Int, // 1
                @SerializedName("parentKey")
                val parentKey: String, // /library/metadata/781
                @SerializedName("parentRatingKey")
                val parentRatingKey: String, // 781
                @SerializedName("parentThumb")
                val parentThumb: String, // /library/metadata/781/thumb/1681973931
                @SerializedName("parentTitle")
                val parentTitle: String, // 季 1
                @SerializedName("primaryExtraKey")
                val primaryExtraKey: String, // /library/metadata/700
                @SerializedName("Producer")
                val producer: List<Producer>,
                @SerializedName("Rating")
                val rating: List<Rating>,
                @SerializedName("ratingKey")
                val ratingKey: String, // 782
                @SerializedName("Role")
                val role: List<Role>,
                @SerializedName("studio")
                val studio: String, // Fox Television Animation
                @SerializedName("tagline")
                val tagline: String, // Don't Die Laughing. We Could Get Sued (Season 4).
                @SerializedName("theme")
                val theme: String, // /library/metadata/376/theme/1681966611
                @SerializedName("thumb")
                val thumb: String, // /library/metadata/782/thumb/1681973931
                @SerializedName("title")
                val title: String, // 聪明狗狗
                @SerializedName("type")
                val type: String, // episode
                @SerializedName("updatedAt")
                val updatedAt: Long, // 1681973931
                @SerializedName("viewedLeafCount")
                val viewedLeafCount: Int, // 0
                @SerializedName("Writer")
                val writer: List<Writer>,
                @SerializedName("year")
                val year: Int, // 2013
            ) {
                @Keep
                data class Country(
                    @SerializedName("filter")
                    val filter: String, // country=208
                    @SerializedName("id")
                    val id: Int, // 208
                    @SerializedName("tag")
                    val tag: String, // United States of America
                )

                @Keep
                data class Director(
                    @SerializedName("filter")
                    val filter: String, // director=2182
                    @SerializedName("id")
                    val id: Int, // 2182
                    @SerializedName("tag")
                    val tag: String, // John Rice
                )

                @Keep
                data class Genre(
                    @SerializedName("filter")
                    val filter: String, // genre=6
                    @SerializedName("id")
                    val id: Int, // 6
                    @SerializedName("tag")
                    val tag: String, // 动画
                )

                @Keep
                data class Guid(
                    @SerializedName("id")
                    val id: String, // imdb://tt3333824
                )

                @Keep
                data class Location(
                    @SerializedName("path")
                    val path: String, // /Users/shuike/投影仪共享/影院/Family guy (1999-)
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
                    val id: Int, // 759
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
                        val `file`: String, // /Users/shuike/投影仪共享/影院/Rick and Morty (2013)/Season 01/Family.Guy.S01E02.I.Never.Met.the.Dead.Man.SDTV.mp4
                        @SerializedName("has64bitOffsets")
                        val has64bitOffsets: Boolean, // false
                        @SerializedName("id")
                        val id: Int, // 759
                        @SerializedName("key")
                        val key: String, // /library/parts/759/1681753571/file.mp4
                        @SerializedName("optimizedForStreaming")
                        val optimizedForStreaming: Boolean, // true
                        @SerializedName("size")
                        val size: Long, // 115569845
                        @SerializedName("Stream")
                        val stream: List<Stream>,
                        @SerializedName("videoProfile")
                        val videoProfile: String, // high
                    ) {
                        @Keep
                        data class Stream(
                            @SerializedName("bitDepth")
                            val bitDepth: Int, // 8
                            @SerializedName("bitrate")
                            val bitrate: Int, // 554
                            @SerializedName("channels")
                            val channels: Int, // 2
                            @SerializedName("chromaLocation")
                            val chromaLocation: String, // left
                            @SerializedName("chromaSubsampling")
                            val chromaSubsampling: String, // 4:2:0
                            @SerializedName("codec")
                            val codec: String, // h264
                            @SerializedName("codedHeight")
                            val codedHeight: Int, // 480
                            @SerializedName("codedWidth")
                            val codedWidth: Int, // 640
                            @SerializedName("default")
                            val default: Boolean, // true
                            @SerializedName("displayTitle")
                            val displayTitle: String, // 480p (H.264)
                            @SerializedName("extendedDisplayTitle")
                            val extendedDisplayTitle: String, // 480p (H.264)
                            @SerializedName("frameRate")
                            val frameRate: Double, // 24.918
                            @SerializedName("hasScalingMatrix")
                            val hasScalingMatrix: Boolean, // false
                            @SerializedName("height")
                            val height: Int, // 480
                            @SerializedName("id")
                            val id: Int, // 1531
                            @SerializedName("index")
                            val index: Int, // 0
                            @SerializedName("level")
                            val level: Int, // 30
                            @SerializedName("profile")
                            val profile: String, // high
                            @SerializedName("refFrames")
                            val refFrames: Int, // 4
                            @SerializedName("samplingRate")
                            val samplingRate: Int, // 44100
                            @SerializedName("scanType")
                            val scanType: String, // progressive
                            @SerializedName("selected")
                            val selected: Boolean, // true
                            @SerializedName("streamIdentifier")
                            val streamIdentifier: String, // 1
                            @SerializedName("streamType")
                            val streamType: Int, // 1
                            @SerializedName("width")
                            val width: Int, // 640
                        )
                    }
                }

                @Keep
                data class Producer(
                    @SerializedName("filter")
                    val filter: String, // producer=2184
                    @SerializedName("id")
                    val id: Int, // 2184
                    @SerializedName("tag")
                    val tag: String, // Ryan Ridley
                )

                @Keep
                data class Rating(
                    @SerializedName("image")
                    val image: String, // themoviedb://image.rating
                    @SerializedName("type")
                    val type: String, // audience
                    @SerializedName("value")
                    val value: Double, // 8.1
                )

                @Keep
                data class Role(
                    @SerializedName("filter")
                    val filter: String, // actor=2052
                    @SerializedName("id")
                    val id: Int, // 2052
                    @SerializedName("role")
                    val role: String, // Snuffles / Centaur / Dog Soldier 1 (voice)
                    @SerializedName("tag")
                    val tag: String, // Rob Paulsen
                    @SerializedName("tagKey")
                    val tagKey: String, // 5d77683454f42c001f8c427d
                    @SerializedName("thumb")
                    val thumb: String, // https://metadata-static.plex.tv/2/people/21a8f104941edbbb45d993c654ac618a.jpg
                )

                @Keep
                data class Writer(
                    @SerializedName("filter")
                    val filter: String, // writer=2183
                    @SerializedName("id")
                    val id: Int, // 2183
                    @SerializedName("tag")
                    val tag: String, // Ryan Ridley
                )
            }
        }
    }
}