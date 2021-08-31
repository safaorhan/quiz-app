package com.hoopow.apps.ui.components

import com.airbnb.lottie.LottieDrawable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PopupViewTest {
    @Test
    fun `LOADING type should use correct animation parameters`() {
        val type = PopupView.Type.LOADING

        assertThat(type.animation).isEqualTo(R.raw.loader)
        assertThat(type.repeatCount).isEqualTo(LottieDrawable.INFINITE)
    }

    @Test
    fun `ERROR type should use correct animation parameters`() {
        val type = PopupView.Type.ERROR

        assertThat(type.animation).isEqualTo(R.raw.bad)
        assertThat(type.repeatCount).isEqualTo(0) // should not repeat
    }
}