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
                val uuid = java.util.UUID.randomUUID().toString().replace("-", "")
                _uuid = uuid
                sharedPreferences.edit {
                    putString("uuid", _uuid)
                }
                uuid
            } else {
                _uuid = uuid
                uuid
            }
        }
}