package com.skit.mplex

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.skit.mplex.ktx.dp
import com.skit.mplex.view.PlayerViewParent

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(PlayerViewParent(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            background = ColorDrawable(Color.RED)
            addView(Button(this@TestActivity).apply {
                text = "Text"
                layoutParams = ViewGroup.LayoutParams(
                    100.dp,
                    50.dp
                )
            })
        })
    }
}