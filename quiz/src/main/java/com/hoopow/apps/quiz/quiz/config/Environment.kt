package com.hoopow.apps.quiz.quiz.config

import androidx.annotation.DrawableRes
import com.hoopow.apps.quiz.quiz.R

enum class Environment(@DrawableRes val background: Int) {
    GRAND_LAGOON(R.drawable.bg_grand_lagoon),
    LANGKAWI_ISLAND(R.drawable.bg_langkawi_island),
    NORTH_ATLANTIC(R.drawable.bg_north_atlantic)
}