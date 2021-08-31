package com.hoopow.apps.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.hoopow.apps.ui.components.databinding.PopupViewBinding

class PopupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var state = State(Type.LOADING, R.string.empty)
        set(value) {
            field = value
            binding.state = value
        }

    var binding = PopupViewBinding.inflate(LayoutInflater.from(context), this, true).also {
        it.state = state
    }

    enum class Type(
        val value: Int,
        @RawRes val animation: Int,
        indeterminate: Boolean
    ) {
        LOADING(0, R.raw.loader, true),
        ERROR(1, R.raw.bad, false);

        val repeatCount = if (indeterminate) INFINITE else 0
    }

    data class State(
        val type: Type,
        @StringRes val message: Int
    )
}