package com.skit.mplex.view

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Typeface
import android.media.AudioManager
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewOutlineProvider
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.skit.mplex.ktx.dp
import com.skit.mplex.view.PlayerViewParent.Type.DURATION
import com.skit.mplex.view.PlayerViewParent.Type.LIGHT
import com.skit.mplex.view.PlayerViewParent.Type.NONE
import com.skit.mplex.view.PlayerViewParent.Type.VOLUME
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class PlayerViewParent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var lightStep: Float = 0f
    private var startLight: Float = 0f
    private var window: Window? = null
    private var startVolume: Int = 0
    private var volumeStep: Int = 0
    private var audioManager: AudioManager
    private var streamMaxVolume: Int = 0
    private var duration: Long = 0L
    private var fl: Long = 0
    private var textView: TextView? = null
    private val TAG = "PlayerViewParent"
    val scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var startX = 0f
    private var startY = 0f
    private var step = 0L

    var handle = false

    //    var volumeCallback: (Int) -> Unit = {}
    var seekCallback: (Long) -> Unit = {}
    var textStrCallback: (Boolean, Long) -> String = { _, _ -> "" }

    private var currentType: Type = NONE

    init {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    enum class Type {
        NONE,
        VOLUME,
        LIGHT,
        DURATION
    }

    fun setDuration(duration: Long) {
        this.duration = duration
        step = duration / measuredWidth
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        volumeStep = measuredHeight / streamMaxVolume
        lightStep = measuredHeight / 100f
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev ?: return super.onInterceptTouchEvent(ev)
        val x = ev.x
        val y = ev.y
        if (ev.action == MotionEvent.ACTION_DOWN) {
            startX = ev.x
            startY = ev.y
            return super.onInterceptTouchEvent(ev)
        }
        if (ev.action == MotionEvent.ACTION_MOVE) {
            val xSwipe = abs(x - startX)
            val ySwipe = abs(y - startY)
            when {
                ySwipe > scaledTouchSlop -> {
                    if (x >= (measuredWidth / 3) * 2) { // 大于等于2/3为音量
                        currentType = VOLUME
                        textView?.apply {
                            text = ""
                            isVisible = true
                            alpha = 0f
                            animate().alpha(1f).start()
                        }
                    }
                    if (x <= measuredWidth / 3) { // 小于等于1/3为亮度
                        currentType = LIGHT
                        textView?.apply {
                            text = ""
                            isVisible = true
                            alpha = 0f
                            animate().alpha(1f).start()
                        }
                    }
                    return true
                }

                xSwipe > scaledTouchSlop -> {
                    currentType = DURATION
                    textView?.apply {
                        text = ""
                        isVisible = true
                        alpha = 0f
                        animate().alpha(1f).start()
                    }
                    return true
                }

                else -> Log.d(TAG, "onInterceptTouchEvent: ${handle} ${scaledTouchSlop} ${xSwipe}")
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private var lastX = 0f
    private var lastY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(event)
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = x
                startY = y

                lastX = x
                lastY = y

                textView?.apply {
                    text = ""
                    isVisible = true
                    alpha = 0f
                    animate().alpha(1f).start()
                }
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                when (currentType) {
                    NONE -> {}
                    VOLUME -> {
                        if (startVolume == 0) {
                            startVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                        }
                        textView?.apply {
                            val volumeOffset = ((y - startY) / volumeStep).roundToInt()
                            var endVolume = startVolume + (-volumeOffset)
                            if (endVolume > streamMaxVolume) {
                                endVolume = streamMaxVolume
                            }
                            if (endVolume < 0) {
                                endVolume = 0
                            }
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, endVolume, 0)
//                            volumeCallback(endVolume)
                            text = "$endVolume"
                        }
                    }

                    LIGHT -> {
                        if (startLight == 0f) {
                            startLight = abs(getCurrentScreenLight())
                        }
                        textView?.apply {
                            val lightOffset = ((y - startY) / lightStep)
                            var endLight = startLight + (-lightOffset)
                            if (endLight > 100f) {
                                endLight = 100f
                            }
                            if (endLight < 0) {
                                endLight = 0f
                            }
                            setScreenLight(endLight / 100f)
                            text = "%.0f".format(endLight)
                        }
                    }

                    DURATION -> {
                        if (duration == 0L) {
                            return super.onTouchEvent(event)
                        }
                        val isLeft: Boolean = if ((x - startX) < 0) {
                            true
                        } else {
                            false
                        }
                        Log.d(TAG, "onTouchEvent: ${x < lastX} ${x - startX}")
                        fl = (((x - startX) / step) * 60L).roundToLong()
                        textView?.apply {
                            text = textStrCallback(isLeft, fl)
                        }
                        lastX = x
                    }
                }
                return true
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "onTouchEvent: ACTION_DOWN")
                when (currentType) {
                    NONE -> {}

                    VOLUME -> {
                        startVolume = 0
                    }

                    LIGHT -> {
                        startLight = 0f
                    }

                    DURATION -> {
                        seekCallback(fl)
                        startX = x
                    }
                }
                currentType = NONE
                textView?.apply {
                    animate().alpha(0f).withEndAction {
                        alpha = 1f
                        isInvisible = true
                        text = ""
                    }.start()
                }
            }
        }
        return true
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        initTextView()
    }

    private fun initTextView() {
        textView = TextView(context).apply {
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#80000000"))
            minWidth = 80.dp
            layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 80.dp).also {
                it.gravity = Gravity.CENTER
            }
            updatePadding(left = 16.dp, right = 16.dp)
            isInvisible = true
            gravity = Gravity.CENTER
            setTypeface(null, Typeface.BOLD)
            textSize = 32f
            clipToOutline = true
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    view ?: return
                    outline ?: return
                    outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, 16f.dp)
                }
            }
        }
        addView(textView)
    }

    private fun getCurrentScreenLight(): Float =
        ((window?.attributes?.screenBrightness) ?: 0f) * 100f

    private fun setScreenLight(endLight: Float) {
        val window = window ?: return
        val attributes = window.attributes
        attributes.screenBrightness = endLight
        window.attributes = attributes
    }

    fun bindWindow(window: Window?) {
        this.window = window
    }
}