package com.hoopow.apps.quiz.quiz.config

import com.hoopow.apps.quiz.quiz.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EnvironmentTest {
    @Test
    fun `GRAND_LAGOON should have correct background`() {
        assertThat(Environment.GRAND_LAGOON.background).isEqualTo(R.drawable.bg_grand_lagoon)
    }

    @Test
    fun `LANGKAWI_ISLAND should have correct background`() {
        assertThat(Environment.LANGKAWI_ISLAND.background).isEqualTo(R.drawable.bg_langkawi_island)
    }

    @Test
    fun `NORTH_ATLANTIC should have correct background`() {
        assertThat(Environment.NORTH_ATLANTIC.background).isEqualTo(R.drawable.bg_north_atlantic)
    }
}