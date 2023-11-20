package com.skit.mplex

import android.app.Application
import com.skit.mplex.storage.SharedStorage

class MPlexApp : Application() {
    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        SharedStorage.init(this)
    }
}