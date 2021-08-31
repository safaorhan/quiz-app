package com.hoopow.apps.splash

import androidx.navigation.NavDirections
import app.cash.turbine.test
import com.hoopow.apps.test.CoroutinesTestRule
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class SplashViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `should navigate to next screen after 1 second`() = runBlocking {
        val viewModel = SplashViewModel()

        viewModel.navigation.navigateTo.test {
            val initialDestination = expectItem()
            assertThat(initialDestination).isNull()

            // After 1 second passes
            coroutinesTestRule.testDispatcher.advanceTimeBy(1000)

            val updatedDestination = expectItem() as NavDirections
            assertThat(updatedDestination.actionId).isEqualTo(R.id.splash_completed)
        }
    }

    @Test
    fun `should navigate to next screen if user clicks the screen`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val viewModel = SplashViewModel()

            viewModel.navigation.navigateTo.test {
                val initialDestination = expectItem()
                assertThat(initialDestination).isNull()

                // Simulate click
                viewModel.onScreenClick()

                val updatedDestination = expectItem() as NavDirections
                assertThat(updatedDestination.actionId).isEqualTo(R.id.splash_completed)
            }
        }
}