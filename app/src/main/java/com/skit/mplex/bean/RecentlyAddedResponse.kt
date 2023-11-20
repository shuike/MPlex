package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class RecentlyAddedResponse(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer,
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // false
        @SerializedName("identifier")
        val identifier: String, // com.plexapp.plugins.library
        @SerializedName("mediaTagPrefix")
        val mediaTagPrefix: String, // /system/bundle/media/flags/
        @SerializedName("mediaTagVersion")
        val mediaTagVersion: Long, // 1681751703
        @SerializedName("Metadata")
        val metadata: List<Metadata>,
        @SerializedName("mixedParents")
        val mixedParents: Boolean, // true
        @SerializedName("size")
        val size: Long, // 22
    ) {
        @Keep
        data class Metadata(
            @SerializedName("addedAt")
            val addedAt: Long, // 1681978051
            @SerializedName("allowSync")
            val allowSync: Boolean, // true
            @SerializedName("art")
            val art: String, // /library/metadata/805/art/1681978052
            @SerializedName("audienceRating")
            val audienceRating: Double, // 9.2
            @SerializedName("audienceRatingImage")
            val audienceRatingImage: String, // rottentomatoes://image.rating.upright
            @SerializedName("contentRating")
            val contentRating: String, // PG-13
            @SerializedName("Country")
            val country: List<Country>,
            @SerializedName("Director")
            val director: List<Director>,
            @SerializedName("duration")
            val duration: Long, // 1348614
            @SerializedName("Genre")
            val genre: List<Genre>,
            @SerializedName("guid")
            val guid: String, // plex://season/602e626fc96042002d07b09e
            @SerializedName("hasPremiumExtras")
            val hasPremiumExtras: String, // 1
            @SerializedName("hasPremiumPrimaryExtra")
            val hasPremiumPrimaryExtra: String, // 1
            @SerializedName("index")
            val index: Int, // 1
            @SerializedName("key")
            val key: String, // /library/metadata/806/children
            @SerializedName("lastViewedAt")
            val lastViewedAt: Long, // 1681989651
            @SerializedName("leafCount")
            val leafCount: Int, // 1
            @SerializedName("librarySectionID")
            val librarySectionID: Int, // 2
            @SerializedName("librarySectionTitle")
            val librarySectionTitle: String, // 电视节目
            @SerializedName("librarySectionUUID")
            val librarySectionUUID: String, // f49eb332-c014-4497-af1c-a08b821988d5
            @SerializedName("Media")
            val media: List<Media>,
            @SerializedName("originalTitle")
            val originalTitle: String, // Avatar: The Way of Water
            @SerializedName("originallyAvailableAt")
            val originallyAvailableAt: String, // 2022-12-14
            @SerializedName("parentGuid")
            val parentGuid: String, // plex://show/5d9c08382192ba001f30a9c7
            @SerializedName("parentIndex")
            val parentIndex: Int, // 1
            @SerializedName("parentKey")
            val parentKey: String, // /library/metadata/805
            @SerializedName("parentRatingKey")
            val parentRatingKey: String, // 805
            @SerializedName("parentStudio")
            val parentStudio: String, // Williams Street
            @SerializedName("parentSummary")
            val parentSummary: String, // 天才兼疯子科学家 Rick 在失踪多年后突然回到女儿 Beth 的身边，并且在她的车库里搞了一个科学实验室。Rick 有一把“传送门枪”，可以穿越到宇宙的各个次元。孙子 Morty 莫名其妙就成了他的助手，经常被拖进他的自制太空船内，跟他一同开展各种疯狂刺激的宇宙冒险。女儿女婿对他的疯狂行为感到不满，却又对这个天才科学家无可奈何。
            @SerializedName("parentTheme")
            val parentTheme: String, // /library/metadata/805/theme/1681978052
            @SerializedName("parentThumb")
            val parentThumb: String, // /library/metadata/805/thumb/1681978052
            @SerializedName("parentTitle")
            val parentTitle: String, // 瑞克和莫蒂
            @SerializedName("parentYear")
            val parentYear: Int, // 2013
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
            @SerializedName("rating")
            val rating: Double, // 7.6
            @SerializedName("ratingImage")
            val ratingImage: String, // rottentomatoes://image.rating.ripe
            @SerializedName("ratingKey")
            val ratingKey: String, // 806
            @SerializedName("Role")
            val role: List<Role>,
            @SerializedName("studio")
            val studio: String, // 20th Century Studios
            @SerializedName("summary")
            val summary: String, // 天才兼疯子科学家Rick在失踪多年后突然回到女儿Beth的身边，并且在她的车库里搞了一个科学实验室。Rick有一把“传送门枪”，可以穿越到宇宙的各个次元。外孙Morty莫名其妙就成了他的助手，经常被拖进他的自制太空船内，跟他一同开展各种疯狂刺激的宇宙冒险。女儿女婿对他的 疯狂行为感到不满，却又对这个天才科学家无可奈何。
            @SerializedName("tagline")
            val tagline: String, // 传奇导演詹姆斯·卡梅隆全新巨作
            @SerializedName("thumb")
            val thumb: String, // /library/metadata/806/thumb/1681978052
            @SerializedName("title")
            val title: String, // 季 1
            @SerializedName("type")
            val type: String, // season
            @SerializedName("updatedAt")
            val updatedAt: Long, // 1681978052
            @SerializedName("viewCount")
            val viewCount: Int, // 2
            @SerializedName("viewOffset")
            val viewOffset: Long, // 130000
            @SerializedName("viewedLeafCount")
            val viewedLeafCount: Int, // 1
            @SerializedName("Writer")
            val writer: List<Writer>,
            @SerializedName("year")
            val year: Int, // 2022
            @SerializedName("childCount")
            val childCount: Int, // 20
        ) {
            @Keep
            data class Country(
                @SerializedName("tag")
                val tag: String, // United States of America
            )

            @Keep
            data class Director(
                @SerializedName("tag")
                val tag: String, // James Cameron
            )

            @Keep
            data class Genre(
                @SerializedName("tag")
                val tag: String, // Action
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
                val bitrate: Int, // 682
                @SerializedName("container")
                val container: String, // mp4
                @SerializedName("duration")
                val duration: Long, // 1348614
                @SerializedName("has64bitOffsets")
                val has64bitOffsets: Boolean, // false
                @SerializedName("height")
                val height: Int, // 480
                @SerializedName("id")
                val id: Int, // 763
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
                    val duration: Long, // 1348614
                    @SerializedName("file")
                    val `file`: String, // /Users/shuike/投影仪共享/电影/Avatar The Way of Water (2022)/Avatar The Way of Water.mp4
                    @SerializedName("has64bitOffsets")
                    val has64bitOffsets: Boolean, // false
                    @SerializedName("id")
                    val id: Int, // 763
                    @SerializedName("key")
                    val key: String, // /library/parts/763/1681753571/file.mp4
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
                val tag: String, // Sam Worthington
            )

            @Keep
            data class Writer(
                @SerializedName("tag")
                val tag: String, // Josh Friedman
            )
        }
    }
}