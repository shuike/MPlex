package com.skit.mplex.ktx

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import coil.drawable.CrossfadeDrawable
import coil.load
import coil.transition.TransitionTarget
import com.skit.mplex.BlurTransformation
import com.skit.mplex.net.plexUrlAddToken

fun ImageView.loadPlexImg(path: String, placeHolder: Any? = null) {
    Log.d("ImageView", "loadPlexImg: ${path.plexUrlAddToken()}")
    load(path.plexUrlAddToken()) {
        if (placeHolder != null) {
            when (placeHolder) {
                is Int -> {
                    placeholder(placeHolder)
                }

                is Drawable -> {
                    placeholder(placeHolder)
                }

                else -> error("不支持的placeHolder类型")
            }
        }
    }
}

fun ImageView.loadPlexImgBlur(path: String, radius: Int = 10) {
    val imageView = this
    load(
        path.plexUrlAddToken(),
        builder = {
            target(object : TransitionTarget {
                override val drawable get() = imageView.drawable
                override val view get() = imageView
                override fun onSuccess(result: Drawable) {
                    val drawable = if (result is Animatable) result else CrossfadeDrawable(
                        imageView.drawable,
                        result,
                        durationMillis = 300
                    )
                    imageView.setImageDrawable(drawable)
                    (drawable as? Animatable)?.start()
                }
            })
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                setRenderEffect(
//                    RenderEffect.createBlurEffect(
//                        radius.toFloat(),
//                        radius.toFloat(),
//                        Shader.TileMode.CLAMP
//                    )
//                )
//            } else {
//                transformations(BlurTransformation(context, radius))
//            }
            transformations(BlurTransformation(context, radius))
        }
    )
}