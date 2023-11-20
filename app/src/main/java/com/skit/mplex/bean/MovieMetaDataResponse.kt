package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class MovieMetaDataResponse(
    @SerializedName("MediaContainer")
    val mediaContainer: MediaContainer,
) {
    @Keep
    data class MediaContainer(
        @SerializedName("allowSync")
        val allowSync: Boolean, // true
        @SerializedName("augmentationKey")
        val augmentationKey: String, // /library/metadata/augmentations/75
        @SerializedName("identifier")
        val identifier: String, // com.plexapp.plugins.library
        @SerializedName("librarySectionID")
        val librarySectionID: Int, // 1
        @SerializedName("librarySectionTitle")
        val librarySectionTitle: String, // 电影
        @SerializedName("librarySectionUUID")
        val librarySectionUUID: String, // d33e33a0-54b6-4281-99e0-61cfd2b21c40
        @SerializedName("mediaTagPrefix")
        val mediaTagPrefix: String, // /system/bundle/media/flags/
        @SerializedName("mediaTagVersion")
        val mediaTagVersion: Long, // 1682023669
        @SerializedName("Metadata")
        val metadata: List<Metadata>,
        @SerializedName("size")
        val size: Int, // 1
    ) {
        @Keep
        data class Metadata(
            @SerializedName("addedAt")
            val addedAt: Long, // 1682920001
            @SerializedName("art")
            val art: String, // /library/metadata/1348/art/1682964303
            @SerializedName("audienceRating")
            val audienceRating: Double, // 8.3
            @SerializedName("audienceRatingImage")
            val audienceRatingImage: String, // rottentomatoes://image.rating.upright
            @SerializedName("Chapter")
            val chapter: List<Chapter>,
            @SerializedName("chapterSource")
            val chapterSource: String, // media
            @SerializedName("contentRating")
            val contentRating: String, // PG-13
            @SerializedName("Country")
            val country: List<Country>,
            @SerializedName("Director")
            val director: List<Director>,
            @SerializedName("duration")
            val duration: Long, // 7474175
            @SerializedName("Extras")
            val extras: Extras,
            @SerializedName("Genre")
            val genre: List<Genre>,
            @SerializedName("guid")
            val guid: String, // plex://movie/5e161a83bea6ac004126e148
            @SerializedName("Guid")
            val guids: List<Guid>,
            @SerializedName("hasPremiumExtras")
            val hasPremiumExtras: String, // 1
            @SerializedName("hasPremiumPrimaryExtra")
            val hasPremiumPrimaryExtra: String, // 1
            @SerializedName("key")
            val key: String, // /library/metadata/1348
            @SerializedName("librarySectionID")
            val librarySectionID: Int, // 1
            @SerializedName("librarySectionKey")
            val librarySectionKey: String, // /library/sections/1
            @SerializedName("librarySectionTitle")
            val librarySectionTitle: String, // 电影
            @SerializedName("Media")
            val media: List<Media>,
            @SerializedName("originalTitle")
            val originalTitle: String, // Ant-Man and the Wasp: Quantumania
            @SerializedName("originallyAvailableAt")
            val originallyAvailableAt: String, // 2023-02-15
            @SerializedName("Preferences")
            val preferences: Preferences,
            @SerializedName("Producer")
            val producer: List<Producer>,
            @SerializedName("rating")
            val rating: Double, // 4.7
            @SerializedName("Rating")
            val ratings: List<Rating>,
            @SerializedName("ratingImage")
            val ratingImage: String, // rottentomatoes://image.rating.rotten
            @SerializedName("ratingKey")
            val ratingKey: String, // 1348
            @SerializedName("Review")
            val review: List<Review>,
            @SerializedName("Role")
            val role: List<Role>,
            @SerializedName("studio")
            val studio: String, // Marvel Studios
            @SerializedName("summary")
            val summary: String, // 超级英雄斯科特·朗 (Scott Lang) 和霍普·凡·戴恩 (Hope Van Dyne) 回归，继续作为蚁人和黄蜂女的冒险。他们与霍普的父母汉克·皮姆 (Hank Pym) 和珍妮特·凡·戴恩 (Janet Van Dyne) ，以及斯科特的女儿凯茜 (Cassie Lang) 一起误入量子领域，并遭遇了漫威电影宇宙最强反派——征服者康 (Kang the Conqueror) ，开始了一场超越他们想象极限的冒险。
            @SerializedName("tagline")
            val tagline: String, // 漫威多元宇宙征服者觉醒 复联超凡搭档迎战新纪元
            @SerializedName("thumb")
            val thumb: String, // /library/metadata/1348/thumb/1682964303
            @SerializedName("title")
            val title: String, // 蚁人与黄蜂女：量子狂潮
            @SerializedName("type")
            val type: String, // movie
            @SerializedName("updatedAt")
            val updatedAt: Long, // 1682964303
            @SerializedName("Writer")
            val writer: List<Writer>,
            @SerializedName("year")
            val year: Int, // 2023
        ) {
            @Keep
            data class Chapter(
                @SerializedName("endTimeOffset")
                val endTimeOffset: Int, // 162996
                @SerializedName("filter")
                val filter: String, // thumb=244
                @SerializedName("id")
                val id: Int, // 244
                @SerializedName("index")
                val index: Int, // 1
                @SerializedName("startTimeOffset")
                val startTimeOffset: Int, // 0
                @SerializedName("thumb")
                val thumb: String, // /library/media/1893/chapterImages/1
            )

            @Keep
            data class Country(
                @SerializedName("filter")
                val filter: String, // country=45
                @SerializedName("id")
                val id: Int, // 45
                @SerializedName("tag")
                val tag: String, // United States of America
            )

            @Keep
            data class Director(
                @SerializedName("filter")
                val filter: String, // director=274
                @SerializedName("id")
                val id: Int, // 274
                @SerializedName("tag")
                val tag: String, // Peyton Reed
            )

            @Keep
            data class Extras(
                @SerializedName("size")
                val size: Int, // 0
            )

            @Keep
            data class Genre(
                @SerializedName("filter")
                val filter: String, // genre=72
                @SerializedName("id")
                val id: Int, // 72
                @SerializedName("tag")
                val tag: String, // 喜剧
            )

            @Keep
            data class Guid(
                @SerializedName("id")
                val id: String, // imdb://tt10954600
            )

            @Keep
            data class Media(
                @SerializedName("aspectRatio")
                val aspectRatio: Double, // 2.35
                @SerializedName("audioChannels")
                val audioChannels: Int, // 6
                @SerializedName("audioCodec")
                val audioCodec: String, // eac3
                @SerializedName("bitrate")                val bitrate: Int, // 21196
                @SerializedName("container")
                val container: String, // mkv
                @SerializedName("duration")
                val duration: Long, // 7474175
                @SerializedName("height")
                val height: Int, // 1608
                @SerializedName("id")
                val id: Int, // 1893
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
                    val duration: Long, // 7474175
                    @SerializedName("file")
                    val `file`: String, // /share/CACHEDEV1_DATA/家庭影院/Video/电影/Ant.Man.and.the.Wasp.Quantumania.2023.2160p.WEB-DL.x265.10bit.SDR.DDP5.1.Atmos-CM/Ant.Man.and.the.Wasp.Quantumania.2023.2160p.WEB-DL.DDP5.1.Atmos.HEVC-CM.mkv
                    @SerializedName("id")
                    val id: Int, // 1893
                    @SerializedName("key")
                    val key: String, // /library/parts/1893/1682749868/file.mkv
                    @SerializedName("size")
                    val size: Long, // 19805114055
                    @SerializedName("Stream")
                    val stream: List<Stream>,
                    @SerializedName("videoProfile")
                    val videoProfile: String, // main 10
                ) {
                    @Keep
                    data class Stream(
                        @SerializedName("audioChannelLayout")
                        val audioChannelLayout: String, // 5.1(side)
                        @SerializedName("bitDepth")
                        val bitDepth: Int, // 10
                        @SerializedName("bitrate")
                        val bitrate: Int, // 20428
                        @SerializedName("channels")
                        val channels: Int, // 6
                        @SerializedName("chromaLocation")
                        val chromaLocation: String, // left
                        @SerializedName("chromaSubsampling")
                        val chromaSubsampling: String, // 4:2:0
                        @SerializedName("codec")
                        val codec: String, // hevc
                        @SerializedName("codedHeight")
                        val codedHeight: Int, // 1608
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
                        @SerializedName("dub")
                        val dub: Boolean, // true
                        @SerializedName("extendedDisplayTitle")
                        val extendedDisplayTitle: String, // 4K (HEVC Main 10)
                        @SerializedName("frameRate")
                        val frameRate: Double, // 23.976
                        @SerializedName("hearingImpaired")
                        val hearingImpaired: Boolean, // true
                        @SerializedName("height")
                        val height: Int, // 1608
                        @SerializedName("id")
                        val id: Int, // 5775
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
                        @SerializedName("original")
                        val original: Boolean, // true
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
                        val title: String, // SDH
                        @SerializedName("width")
                        val width: Int, // 3840
                    )
                }
            }

            @Keep
            data class Preferences(
                @SerializedName("Setting")
                val setting: List<Setting>,
            ) {
                @Keep
                data class Setting(
                    @SerializedName("advanced")
                    val advanced: Boolean, // false
                    @SerializedName("default")
                    val default: String,
                    @SerializedName("enumValues")
                    val enumValues: String, // :Library default|ca-ES:català|cs-CZ:Čeština|da-DK:Dansk|de-DE:Deutsch|et-EE:eesti|en-US:English|en-AU:English (Australia)|en-CA:English (Canada)|en-GB:English (UK)|es-ES:Español|es-MX:Español (México)|fr-FR:Français|fr-CA:Français (Canada)|hr-HR:Hrvatski|id-ID:Indonesia|it-IT:Italiano|lv-LV:latviešu|lt-LT:lietuvių|hu-HU:magyar|nl-NL:Nederlands|nb-NO:norsk bokmål|pl-PL:polski|pt-BR:Português|pt-PT:Português (Portugal)|ro-RO:română|sk-SK:Slovenčina|fi-FI:Suomi|sv-SE:Svenska|vi-VN:Tiếng Việt|tr-TR:Türkçe|el-GR:Ελληνικά|bg-BG:български|ru-RU:Русский|uk-UA:Українська|he-IL:עברית|ar-SA:العربية (المملكة العربية السعودية)|fa-IR:فارسی|hi-IN:हिन्दी|th-TH:ไทย|ko-KR:한국어|zh-CN:中文|zh-HK:中文（中國香港）|zh-TW:中文（台灣）|ja-JP:日本語
                    @SerializedName("group")
                    val group: String,
                    @SerializedName("hidden")
                    val hidden: Boolean, // false
                    @SerializedName("id")
                    val id: String, // languageOverride
                    @SerializedName("label")
                    val label: String, // Metadata language
                    @SerializedName("summary")
                    val summary: String, // Language to use for item metadata such as synopsis and title.
                    @SerializedName("type")
                    val type: String, // text
                    @SerializedName("value")
                    val value: String,
                )
            }

            @Keep
            data class Producer(
                @SerializedName("filter")
                val filter: String, // producer=364
                @SerializedName("id")
                val id: Int, // 364
                @SerializedName("tag")
                val tag: String, // Kevin Feige
            )

            @Keep
            data class Rating(
                @SerializedName("image")
                val image: String, // imdb://image.rating
                @SerializedName("type")
                val type: String, // audience
                @SerializedName("value")
                val value: Double, // 6.3
            )

            @Keep
            data class Review(
                @SerializedName("filter")
                val filter: String, // art=440
                @SerializedName("id")
                val id: Int, // 440
                @SerializedName("image")
                val image: String, // rottentomatoes://image.review.rotten
                @SerializedName("link")
                val link: String, // https://www.newyorker.com/culture/the-front-row/ant-man-and-the-wasp-quantumania-is-prefab-marvel
                @SerializedName("source")
                val source: String, // New Yorker
                @SerializedName("tag")
                val tag: String, // Richard Brody
                @SerializedName("text")
                val text: String, // The hordes of visual-effects artists whose names roll by in the infinity credits are the veritable auteurs of the film, with no overarching sensibility to guide them.
            )

            @Keep
            data class Role(
                @SerializedName("filter")
                val filter: String, // actor=284
                @SerializedName("id")
                val id: Int, // 284
                @SerializedName("role")
                val role: String, // Scott Lang / Ant-Man
                @SerializedName("tag")
                val tag: String, // Paul Rudd
                @SerializedName("tagKey")
                val tagKey: String, // 5d77682685719b001f3a0b81
                @SerializedName("thumb")
                val thumb: String, // https://metadata-static.plex.tv/people/5d77682685719b001f3a0b81.jpg
            )

            @Keep
            data class Writer(
                @SerializedName("filter")
                val filter: String, // writer=7365
                @SerializedName("id")
                val id: Int, // 7365
                @SerializedName("tag")
                val tag: String, // Jeff Loveness
            )
        }
    }
}