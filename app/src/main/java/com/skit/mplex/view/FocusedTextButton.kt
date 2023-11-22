package com.skit.mplex.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.skit.mplex.R
import com.skit.mplex.databinding.ViewFocusedTextButtonBinding

class FocusedTextButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {
    companion object {
        private const val TAG = "FocusedTextButton"
    }

    private var binding: ViewFocusedTextButtonBinding
    private var tintColor: Int = 0
    private var focusedTintColor: Int = 0

    init {
        binding =
            ViewFocusedTextButtonBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)

        val attributes =
            context.obtainStyledAttributes(attrs, R.styleable.FocusedTextButton)
        val text = attributes.getString(R.styleable.FocusedTextButton_text)
        tintColor = attributes.getColor(R.styleable.FocusedTextButton_tint, Color.WHITE)
        focusedTintColor =
            attributes.getColor(R.styleable.FocusedTextButton_focusedTint, Color.WHITE)
        val id: Int = attributes.getResourceId(R.styleable.FocusedTextButton_srcCompat, -1)
        if (id != -1) {
            val drawable = AppCompatResources.getDrawable(context, id)
            if (drawable != null) {
                binding.icon.setImageDrawable(drawable)
            }
        }

        binding.icon.imageTintList = ColorStateList.valueOf(tintColor)
        binding.text.text = text

        attributes.recycle()
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        Log.d("TAG", "onFocusChanged: ${gainFocus}")
        if (gainFocus) {
            binding.text.isVisible = true
            binding.icon.imageTintList = ColorStateList.valueOf(focusedTintColor)
        } else {
            binding.text.isGone = true
            binding.icon.imageTintList = ColorStateList.valueOf(tintColor)
        }
    }
}