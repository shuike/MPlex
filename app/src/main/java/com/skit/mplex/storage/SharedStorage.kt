package com.skit.mplex.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedStorage {
    lateinit var sharedPreference: SharedPreferences
    fun init(application: Application) {
        sharedPreference = application.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    }


    var host: String
        get() = sharedPreference.getString("host", "") ?: ""
        set(value) {
            sharedPreference.edit {
                putString("host", value)
            }
        }

    var token: String
        get() = sharedPreference.getString("token", "") ?: ""
        set(value) {
            sharedPreference.edit {
                putString("token", value)
            }
        }

}