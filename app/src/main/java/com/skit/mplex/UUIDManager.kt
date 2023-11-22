package com.skit.mplex

import android.content.Context
import androidx.core.content.edit

object UUIDManager {
    private var _uuid = ""
    val UUID: String
        get() {
            if (_uuid.isNotEmpty()) return _uuid
            val sharedPreferences =
                MPlexApp.application.getSharedPreferences("preferences", Context.MODE_PRIVATE)
            val uuid = sharedPreferences.getString("uuid", null)
            return if (uuid.isNullOrEmpty()) {
                _uuid = java.util.UUID.randomUUID().toString().replace("-", "")
                sharedPreferences.edit(true) {
                    putString("uuid", _uuid)
                }
                _uuid
            } else {
                _uuid = uuid
                uuid
            }
        }
}