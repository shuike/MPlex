package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class TVShowChildMetaDataResponse(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // true
        @SerializedName("augmentationKey")
        val augmentationKey: String, // /library/metadata/augmentations/137
        @SerializedName("identifier")
        val identifier: String, // com.plexapp.plugins.library
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
        @SerializedName("size")
        val size: Int // 1
    ) {
        @Keep
        data class Metadata(
            @SerializedName("addedAt")
            val addedAt: Long, // 1682919926
            @SerializedName("art")
            val art: String?, // /library/metadata/1312/art/1683140399
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
            @SerializedName("Extras")
            val extras: Extras,
            @SerializedName("grandparentArt")
            val grandparentArt: String, // /library/metadata/1312/art/1683140399
            @SerializedName("grandparentGuid")
            val grandparentGuid: String, // plex://show/5ecc3ba48e44b0004171c15e
            @SerializedName("grandparentKey")
            val grandparentKey: String, // /library/metadata/1312
            @SerializedName("grandparentRatingKey")
            val grandparentRatingKey: String, // 1312
            @SerializedName("grandparentTheme")
            val grandparentTheme: String, // /library/metadata/1312/theme/1683140399
            @SerializedName("grandparentThumb")
            val grandparentThumb: String, // /library/metadata/1312/thumb/1683140399
            @SerializedName("grandparentTitle")
            val grandparentTitle: String, // 鹿角男孩
            @SerializedName("guid")
            val guid: String, // plex://episode/5ecc3ba58e44b0004171c198
            @SerializedName("Guid")
            val guids: List<Guid>,
            @SerializedName("index")
            val index: Int, // 1
            @SerializedName("key")
            val key: String, // /library/metadata/1314
            @SerializedName("lastViewedAt")
            val lastViewedAt: Long, // 1682920044
            @SerializedName("librarySectionID")
            val librarySectionID: Int, // 3
            @SerializedName("librarySectionKey")
            val librarySectionKey: String, // /library/sections/3
            @SerializedName("librarySectionTitle")
            val librarySectionTitle: String, // 电视剧
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
            val parentTitle: String, // Season 1
            @SerializedName("Producer")
            val producer: List<Producer>,
            @SerializedName("Rating")
            val rating: List<Rating>,
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
            val year: Int // 2021
        ) {
            @Keep
            data class Director(
                @SerializedName("filter")
                val filter: String, // director=7548
                @SerializedName("id")
                val id: Int, // 7548
                @SerializedName("tag")
                val tag: String // Jim Mickle
            )

            @Keep
            data class Extras(
                @SerializedName("size")
                val size: Int // 0
            )

            @Keep
            data class Guid(
                @SerializedName("id")
                val id: String // imdb://tt9308104
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
                val width: Int // 3840
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
                    @SerializedName("Stream")
                    val stream: List<Stream>,
                    @SerializedName("videoProfile")
                    val videoProfile: String // main 10
                ) {
                    @Keep
                    data class Stream(
                        @SerializedName("audioChannelLayout")
                        val audioChannelLayout: String, // 5.1(side)
                        @SerializedName("bitDepth")
                        val bitDepth: Int, // 10
                        @SerializedName("bitrate")
                        val bitrate: Int, // 11419
                        @SerializedName("channels")
                        val channels: Int, // 6
                        @SerializedName("chromaLocation")
                        val chromaLocation: String, // left
                        @SerializedName("chromaSubsampling")
                        val chromaSubsampling: String, // 4:2:0
                        @SerializedName("codec")
                        val codec: String, // hevc
                        @SerializedName("format")
                        val format: String?, // ass
                        @SerializedName("key")
                        val key: String?, // /library/streams/5097
                        @SerializedName("codedHeight")
                        val codedHeight: Int, // 2160
                        @SerializedName("codedWidth")
                        val codedWidth: Int, // 3840
                        @SerializedName("colorPrimaries")
                        val colorPrimaries: String, // bt709
                        @SerializedName("colorRange")
                        val colorRange: String, // tv
                        @SerializedName("colorSpace")
                        val colorSpace: String, // bt709
                        @SerializedName("colorTrc")
                        val colorTrc: String, // bt709
                        @SerializedName("default")
                        val default: Boolean, // true
                        @SerializedName("displayTitle")
                        val displayTitle: String, // 4K (HEVC Main 10)
                        @SerializedName("extendedDisplayTitle")
                        val extendedDisplayTitle: String, // 4K (HEVC Main 10)
                        @SerializedName("frameRate")
                        val frameRate: Double, // 23.976
                        @SerializedName("height")
                        val height: Int, // 2160
                        @SerializedName("id")
                        val id: Int, // 5355
                        @SerializedName("index")
                        val index: Int, // 0
                        @SerializedName("language")
                        val language: String, // English
                        @SerializedName("languageCode")
                        val languageCode: String, // eng
                        @SerializedName("languageTag")
                        val languageTag: String, // en
                        @SerializedName("level")
                        val level: Int, // 150
                        @SerializedName("profile")
                        val profile: String, // main 10
                        @SerializedName("refFrames")
                        val refFrames: Int, // 1
                        @SerializedName("samplingRate")
                        val samplingRate: Int, // 48000
                        @SerializedName("selected")
                        val selected: Boolean, // true
                        @SerializedName("streamType")
                        val streamType: Int, // 1
                        @SerializedName("title")
                        val title: String, // English [SDH]
                        @SerializedName("width")
                        val width: Int // 3840
                    )
                }
            }

            @Keep
            data class Producer(
                @SerializedName("filter")
                val filter: String, // producer=7550
                @SerializedName("id")
                val id: Int, // 7550
                @SerializedName("tag")
                val tag: String // Hana Botha
            )

            @Keep
            data class Rating(
                @SerializedName("image")
                val image: String, // themoviedb://image.rating
                @SerializedName("type")
                val type: String, // audience
                @SerializedName("value")
                val value: Double // 6.8
            )

            @Keep
            data class Role(
                @SerializedName("filter")
                val filter: String, // actor=7490
                @SerializedName("id")
                val id: Int, // 7490
                @SerializedName("role")
                val role: String, // Gus (7)
                @SerializedName("tag")
                val tag: String, // Nixon Bingley
                @SerializedName("tagKey")
                val tagKey: String, // 60479d4cf786ca002cc4b23a
                @SerializedName("thumb")
                val thumb: String // https://metadata-static.plex.tv/people/5eb29114d33656004282406a.jpg
            )

            @Keep
            data class Writer(
                @SerializedName("filter")
                val filter: String, // writer=7549
                @SerializedName("id")
                val id: Int, // 7549
                @SerializedName("tag")
                val tag: String // Jim Mickle
            )
        }
    }
}