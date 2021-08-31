package com.hoopow.apps.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.hoopow.apps.ui.components.databinding.StarViewBinding

class StarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var starCount: Int = 0
        set(value) {
            field = value
            binding.starCount = value
        }

    var binding = StarViewBinding.inflate(LayoutInflater.from(context), this, true).also {
        it.starCount = starCount
    }
}