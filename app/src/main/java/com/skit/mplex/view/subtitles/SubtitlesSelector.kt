package com.skit.mplex.view.subtitles

import android.app.Activity
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skit.mplex.R
import com.skit.mplex.ktx.dp
import com.skit.mplex.subtitles.SubtitlesData
import com.skit.mplex.subtitles.SubtitlesSelectManager


class SubtitlesSelector(
    private val subtitlesSelectManager: SubtitlesSelectManager
) {
    val isShown: Boolean
        get() {
            if (!::settingsWindow.isInitialized) return false
            return settingsWindow.isVisible
        }
    private lateinit var recyclerView: RecyclerView
    private lateinit var settingsWindow: ViewGroup
    var onSelect: (SubtitlesData) -> Unit = {}

    fun show(activity: Activity, list: List<SubtitlesData>) {
        if (!::settingsWindow.isInitialized) {
            init(activity)
        }
        recyclerView.adapter = Adapter(list)
        recyclerView.requestFocusFromTouch()
        settingsWindow.alpha = 0f
        settingsWindow.isVisible = true
        settingsWindow.animate().alpha(1f).start()
    }

    private fun init(activity: Activity) {
        recyclerView = RecyclerView(activity)
        recyclerView.layoutParams = FrameLayout.LayoutParams(
            250.dp,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            this.gravity = Gravity.END
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setBackgroundColor(Color.parseColor("#B3000000"))
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        settingsWindow = FrameLayout(activity)
        settingsWindow.layoutParams = ViewGroup.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
        )
        settingsWindow.setBackgroundColor(Color.TRANSPARENT)
        settingsWindow.setOnClickListener {
            hide()
        }
        settingsWindow.addView(recyclerView)
        activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
            .addView(settingsWindow)
    }

    fun hide() {
        if (!::settingsWindow.isInitialized) return
        settingsWindow.animate().alpha(0f).withEndAction {
            settingsWindow.isInvisible = true
            settingsWindow.alpha = 1f
        }.start()
    }

    private inner class Adapter(val list: List<SubtitlesData>) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {
        private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(android.R.id.text1)

            init {
                textView.setTextColor(Color.WHITE)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_subtitles_selector, parent, false)
            )
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = list[position]
            val id = when (data) {
                is SubtitlesData.Inner -> data.id
                is SubtitlesData.Remote -> data.id
            }
            holder.itemView.isSelected = id == subtitlesSelectManager.selectedId
            when (data) {
                is SubtitlesData.Inner -> {
                    holder.textView.text = data.name
                }

                is SubtitlesData.Remote -> {
                    holder.textView.text = data.name
                }
            }
            holder.textView.setOnClickListener {
                onSelect(data)
            }
        }
    }
}