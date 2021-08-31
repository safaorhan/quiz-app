package com.hoopow.apps.ui.components

import com.hoopow.apps.ui.components.CTAButton.Size
import com.hoopow.apps.ui.components.CTAButton.Type
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CtaButtonTest {
    @Test
    fun `small button size should use proper background res`() {
        val size = Size.SMALL

        assertThat(size.backgroundRes).isEqualTo(R.drawable.button_cta_small)
    }

    @Test
    fun `medium button size should use proper background res`() {
        val size = Size.MEDIUM

        assertThat(size.backgroundRes).isEqualTo(R.drawable.button_cta_medium)
    }

    @Test
    fun `large button size should use proper background res`() {
        val size = Size.LARGE

        assertThat(size.backgroundRes).isEqualTo(R.drawable.button_cta_large)
    }

    @Test
    fun `positive button type should use proper color res`() {
        val type = Type.POSITIVE

        assertThat(type.colorRes).isEqualTo(R.color.cta_button_positive)
    }

    @Test
    fun `warning button type should use proper color res`() {
        val type = Type.WARNING

        assertThat(type.colorRes).isEqualTo(R.color.cta_button_warning)
    }

    @Test
    fun `error button type should use proper color res`() {
        val type = Type.ERROR

        assertThat(type.colorRes).isEqualTo(R.color.cta_button_error)
    }

    @Test
    fun `neutral button type should use proper color res`() {
        val type = Type.NEUTRAL

        assertThat(type.colorRes).isEqualTo(R.color.cta_button_neutral)
    }
}