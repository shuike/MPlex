package com.skit.mplex.ktx

import android.content.Context
import android.content.Intent

inline fun <reified T> Context.launch(block: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(block))
}