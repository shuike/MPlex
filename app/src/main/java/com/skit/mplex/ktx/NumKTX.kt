package com.skit.mplex.ktx

import android.content.res.Resources
import android.util.TypedValue
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt


val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

val Int.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

fun Int.toAlphaColor(alpha: Float): Int {
    return ColorUtils.setAlphaComponent(
        this,
        (255 * alpha).roundToInt()
    )
}