package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


@Keep
data class LoginResponse(
    @SerializedName("adsConsent")
    val adsConsent: Any, // null
    @SerializedName("adsConsentReminderAt")
    val adsConsentReminderAt: Any, // null
    @SerializedName("adsConsentSetAt")
    val adsConsentSetAt: Any, // null
    @SerializedName("anonymous")
    val anonymous: Any, // null
    @SerializedName("authToken")
    val authToken: String, // vjc5PdAM8xiWQ6jzve1_
    @SerializedName("backupCodesCreated")
    val backupCodesCreated: Boolean, // false
    @SerializedName("confirmed")
    val confirmed: Boolean, // true
    @SerializedName("country")
    val country: String, // CN
    @SerializedName("email")
    val email: String, // shuike007@126.com
    @SerializedName("emailOnlyAuth")
    val emailOnlyAuth: Boolean, // false
    @SerializedName("entitlements")
    val entitlements: List<Any>,
    @SerializedName("experimentalFeatures")
    val experimentalFeatures: Boolean, // false
    @SerializedName("friendlyName")
    val friendlyName: String,
    @SerializedName("guest")
    val guest: Boolean, // false
    @SerializedName("hasPassword")
    val hasPassword: Boolean, // true
    @SerializedName("home")
    val home: Boolean, // false
    @SerializedName("homeAdmin")
    val homeAdmin: Boolean, // false
    @SerializedName("homeSize")
    val homeSize: Int, // 1
    @SerializedName("id")
    val id: Long, // 186600134
    @SerializedName("joinedAt")
    val joinedAt: Long, // 1670069949
    @SerializedName("locale")
    val locale: Any, // null
    @SerializedName("mailingListActive")
    val mailingListActive: Boolean, // true
    @SerializedName("mailingListStatus")
    val mailingListStatus: String, // active
    @SerializedName("maxHomeSize")
    val maxHomeSize: Long, // 15
    @SerializedName("pastSubscriptions")
    val pastSubscriptions: List<Any>,
    @SerializedName("profile")
    val profile: Profile,
    @SerializedName("protected")
    val `protected`: Boolean, // false
    @SerializedName("rememberExpiresAt")
    val rememberExpiresAt: Long, // 1683518598
    @SerializedName("restricted")
    val restricted: Boolean, // false
    @SerializedName("scrobbleTypes")
    val scrobbleTypes: String,
    @SerializedName("services")
    val services: List<Service>,
    @SerializedName("subscription")
    val subscription: Subscription,
    @SerializedName("subscriptionDescription")
    val subscriptionDescription: Any, // null
    @SerializedName("subscriptions")
    val subscriptions: List<Any>,
    @SerializedName("thumb")
    val thumb: String, // https://plex.tv/users/0378c92060924c57/avatar?c=1677223232
    @SerializedName("title")
    val title: String, // shuike
    @SerializedName("trials")
    val trials: List<Any>,
    @SerializedName("twoFactorEnabled")
    val twoFactorEnabled: Boolean, // false
    @SerializedName("username")
    val username: String, // shuike
    @SerializedName("uuid")
    val uuid: String, // 0378c92060924c57
) {
    @Keep
    data class Profile(
        @SerializedName("autoSelectAudio")
        val autoSelectAudio: Boolean, // true
        @SerializedName("autoSelectSubtitle")
        val autoSelectSubtitle: Int, // 1
        @SerializedName("defaultAudioLanguage")
        val defaultAudioLanguage: String, // zh
        @SerializedName("defaultSubtitleAccessibility")
        val defaultSubtitleAccessibility: Int, // 0
        @SerializedName("defaultSubtitleForced")
        val defaultSubtitleForced: Int, // 0
        @SerializedName("defaultSubtitleLanguage")
        val defaultSubtitleLanguage: String, // zh
    )

    @Keep
    data class Service(
        @SerializedName("endpoint")
        val endpoint: String, // https://epg.provider.plex.tv
        @SerializedName("identifier")
        val identifier: String, // epg
        @SerializedName("secret")
        val secret: String, // H8q1AfgeOcf8+wx2ODos1mLBkmsSE4PMCJkkdx27xNwpzzKVV3up6gZJb+teyIOw
        @SerializedName("status")
        val status: String, // online
        @SerializedName("token")
        val token: String, // RkHIzgbqTjP2giDabsWdbBB9o4YNyhS6CqbTgwfKAOc=
    )

    @Keep
    data class Subscription(
        @SerializedName("active")
        val active: Boolean, // false
        @SerializedName("features")
        val features: List<String>,
        @SerializedName("paymentService")
        val paymentService: Any, // null
        @SerializedName("plan")
        val plan: Any, // null
        @SerializedName("status")
        val status: String, // Inactive
        @SerializedName("subscribedAt")
        val subscribedAt: Any, // null
    )
}