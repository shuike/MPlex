package com.skit.mplex.server

import com.skit.mplex.bean.LoginResponse
import com.skit.mplex.bean.ResourceResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface PlexApi {

    @POST("api/v2/users/signin")
    @FormUrlEncoded
    suspend fun login(
        @Field("login") login: String,
        @Field("password") password: String,
    ): LoginResponse

    @GET("api/v2/resources?includeRelay=1&includeHttps=1")
    suspend fun resources(): ResourceResponse
}