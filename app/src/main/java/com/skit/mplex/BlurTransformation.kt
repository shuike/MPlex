package com.skit.mplex

import android.content.Context
import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation
import com.google.android.renderscript.Toolkit

/**
 * A [Transformation] that applies a Gaussian blur to an image.
 *
 * @param context The [Context]
 * @param radius The radius of the blur.
 * @param sampling The sampling multiplier used to scale the image. Values > 1
 *  will downscale the image. Values between 0 and 1 will upscale the image.
 */

class BlurTransformation @JvmOverloads constructor(
    private val context: Context,
    private val radius: Int = DEFAULT_RADIUS,
    private val sampling: Float = DEFAULT_SAMPLING,
) : Transformation {

    init {
        require(radius in 0..25) { "radius must be in [0, 25]." }
        require(sampling > 0) { "sampling must be > 0." }
    }

    override val cacheKey = "${BlurTransformation::class.java.name}-$radius-$sampling"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        return Toolkit.blur(input, radius)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is BlurTransformation &&
                context == other.context &&
                radius == other.radius &&
                sampling == other.sampling
    }

    override fun hashCode(): Int {
        var result = context.hashCode()
        result = 31 * result + radius.hashCode()
        result = 31 * result + sampling.hashCode()
        return result
    }

    override fun toString(): String {
        return "BlurTransformation(context=$context, radius=$radius, sampling=$sampling)"
    }

    private companion object {
        private const val DEFAULT_RADIUS = 5
        private const val DEFAULT_SAMPLING = 1f
    }


}

internal val Bitmap.safeConfig: Bitmap.Config
    get() = config ?: Bitmap.Config.ARGB_8888