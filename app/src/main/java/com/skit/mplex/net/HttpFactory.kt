package com.skit.mplex.net

import android.os.Build
import android.util.Log
import com.skit.mplex.UUIDManager
import com.skit.mplex.storage.SharedStorage
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

object HttpFactory {
    //    var token: String = ""
//    var host: String = ""
    val HOST: String
        get() {
//            return "http://192.168.31.32:32400"
//            return "https://103-29-70-184.31fc8329186d43b8879f20bc514ba117.plex.direct:8443"
            return SharedStorage.host //"https://172-104-96-77.31fc8329186d43b8879f20bc514ba117.plex.direct:8443"
//            return "http://192.168.55.107:32400"
        }
    val PLEX_TOKEN: String
        get() {
            return SharedStorage.token//"xMdhFuTH6wue8_2L_o6f"
        }
    private const val TAG = "HttpFactory"
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
            val builder =
                request.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("X-Plex-Product", "MPlex for Android")
                    .addHeader("X-Plex-Version", "1.0.0")
                    .addHeader(
                        "X-Plex-Client-Identifier",
                        UUIDManager.UUID.substring(0, 24)
                    )
                    .addHeader("X-Plex-Platadform", "Android")
                    .addHeader("X-Plex-Platform-Version", Build.VERSION.SDK_INT.toString())
                    .addHeader("X-Plex-Device", Build.DEVICE)
                    .addHeader("X-Plex-Language", Locale.getDefault().language)
            if (PLEX_TOKEN.isNotEmpty()) {
                builder.addHeader("X-Plex-Token", PLEX_TOKEN)
            }
            val proceed = it.proceed(builder.build())
            val peekBody = proceed.peekBody(1024 * 1024)
            Log.d(TAG, "Interceptor: url: ${request.url}\n${String(peekBody.bytes())}")
            proceed
        }
        .build()

    val localRetrofit: Retrofit
        get() = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val remoteRetrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://plex.tv")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}