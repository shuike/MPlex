package com.skit.mplex.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ClockBroadcast(val timeCallback: (Long) -> Unit) : BroadcastReceiver(),
    DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        (owner as? Context)?.registerReceiver(this, IntentFilter().apply {
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIME_TICK)
        })
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        (owner as? Context)?.unregisterReceiver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return
        when (intent.action) {
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_TIME_TICK,
            -> {
                timeCallback(System.currentTimeMillis())
            }
        }
    }


}