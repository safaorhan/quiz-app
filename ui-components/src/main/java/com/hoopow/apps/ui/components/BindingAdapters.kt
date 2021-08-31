package com.hoopow.apps.ui.components

import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

@BindingAdapter("animationRes")
fun LottieAnimationView.animation(@RawRes animationRes: Int) {
    setAnimation(animationRes)
    playAnimation()
}

@BindingAdapter("starIndex", "starCount")
fun ImageView.starCount(starIndex: Int, starCount: Int) {
    val shouldBeFilled = starIndex < starCount

    val imgRes = when {
        shouldBeFilled -> R.drawable.ic_star_filled
        else -> R.drawable.ic_star_empty
    }

    setImageResource(imgRes)
}