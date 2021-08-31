package com.hoopow.apps.ui.components

import android.animation.AnimatorInflater.loadStateListAnimator
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.hoopow.apps.ui.components.R.animator

class CTAButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    private var size = Size.SMALL
    private var type = Type.POSITIVE

    init {
        context.withStyledAttributes(attrs, R.styleable.CTAButton) {
            size = Size.fromValue(getInt(R.styleable.CTAButton_size, Size.SMALL.value))
            type = Type.fromValue(getInt(R.styleable.CTAButton_buttonType, Type.POSITIVE.value))
        }

        applySizeAndType()

        applyStateListAnimator()

        applyGravity()

        applyCustomFont()

        applyTextShadow()

        applyTextColor()

        applyTextAllCaps()
    }

    private fun applyTextAllCaps() {
        isAllCaps = false
    }

    private fun applyTextColor() {
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }

    private fun applyTextShadow() {
        val shadowRadius = 0.1f
        val shadowDx = 0f
        val shadowDy = resources.getDimension(R.dimen.button_cta_text_shadow_dy)
        val shadowColor = ContextCompat.getColor(context, R.color.cta_button_text_shadow)
        setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor)
    }

    private fun applyCustomFont() {
        typeface = ResourcesCompat.getFont(context, R.font.grandstander_semibold)
    }

    private fun applyGravity() {
        gravity = Gravity.CENTER
    }

    private fun applyStateListAnimator() {
        stateListAnimator = loadStateListAnimator(context, animator.button_state_list_animator)
    }

    private fun applySizeAndType() {
        setBackgroundResource(size.backgroundRes)

        val foreground = (background as LayerDrawable).findDrawableByLayerId(R.id.foreground)
        foreground.setTint(ContextCompat.getColor(context, type.colorRes))
    }

    enum class Size(
        val value: Int,
        @DrawableRes val backgroundRes: Int
    ) {
        SMALL(0, R.drawable.button_cta_small),
        MEDIUM(1, R.drawable.button_cta_medium),
        LARGE(2, R.drawable.button_cta_large);

        companion object {
            fun fromValue(value: Int) = values().toList().find { it.value == value }!!
        }
    }

    enum class Type(
        val value: Int,
        @ColorRes val colorRes: Int
    ) {
        POSITIVE(0, R.color.cta_button_positive),
        WARNING(1, R.color.cta_button_warning),
        ERROR(2, R.color.cta_button_error),
        NEUTRAL(3, R.color.cta_button_neutral);

        companion object {
            fun fromValue(value: Int) = values().toList().find { it.value == value }!!
        }
    }
}