package com.skit.mplex.bean

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName


class ResourceResponse : ArrayList<ResourceResponse.ResourceResponseItem>() {
    @Keep
    data class ResourceResponseItem(
        @SerializedName("accessToken")
        val accessToken: String, // fyVkm-ta_PEp1hCU8Zdx
        @SerializedName("clientIdentifier")
        val clientIdentifier: String, // 594343be70ba0dd9a594ce8dd25f28271848d4b0
        @SerializedName("connections")
        val connections: List<Connection>,
        @SerializedName("createdAt")
        val createdAt: String, // 2023-04-20T04:18:12Z
        @SerializedName("device")
        val device: String, // MacBookPro18,3
        @SerializedName("dnsRebindingProtection")
        val dnsRebindingProtection: Boolean, // false
        @SerializedName("home")
        val home: Boolean, // false
        @SerializedName("httpsRequired")
        val httpsRequired: Boolean, // false
        @SerializedName("lastSeenAt")
        val lastSeenAt: String, // 2023-04-24T13:31:56Z
        @SerializedName("name")
        val name: String, // shuike的MacBook Pro (2)
        @SerializedName("natLoopbackSupported")
        val natLoopbackSupported: Boolean, // false
        @SerializedName("owned")
        val owned: Boolean, // true
        @SerializedName("ownerId")
        val ownerId: Any, // null
        @SerializedName("platform")
        val platform: String, // MacOSX
        @SerializedName("platformVersion")
        val platformVersion: String, // 13.3.1
        @SerializedName("presence")
        val presence: Boolean, // true
        @SerializedName("product")
        val product: String, // Plex Media Server
        @SerializedName("productVersion")
        val productVersion: String, // 1.32.0.6950-8521b7d99
        @SerializedName("provides")
        val provides: String, // server
        @SerializedName("publicAddress")
        val publicAddress: String, // 111.192.220.201
        @SerializedName("publicAddressMatches")
        val publicAddressMatches: Boolean, // true
        @SerializedName("relay")
        val relay: Boolean, // true
        @SerializedName("sourceTitle")
        val sourceTitle: Any, // null
        @SerializedName("synced")
        val synced: Boolean, // false
    ) {
        @Keep
        data class Connection(
            @SerializedName("address")
            val address: String, // 192.168.31.221
            @SerializedName("IPv6")
            val iPv6: Boolean, // false
            @SerializedName("local")
            val local: Boolean, // true
            @SerializedName("port")
            val port: Int, // 32400
            @SerializedName("protocol")
            val protocol: String, // http
            @SerializedName("relay")
            val relay: Boolean, // false
            @SerializedName("uri")
            val uri: String, // http://192.168.31.221:32400
        )
    }
}