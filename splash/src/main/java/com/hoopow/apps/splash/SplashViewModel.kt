package com.hoopow.apps.splash

import androidx.lifecycle.viewModelScope
import androidx.navigation.ActionOnlyNavDirections
import com.hoopow.apps.infra.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    init {
        delayNavigateToNextScreen()
    }

    private fun delayNavigateToNextScreen() = viewModelScope.launch {
        delay(SPLASH_VISIBLE_DURATION_IN_MS)
        navigateToNextScreen()
    }

    private fun navigateToNextScreen() {
        val directions = ActionOnlyNavDirections(R.id.splash_completed)
        navigation.navigate(directions)
    }

    fun onScreenClick() {
        navigateToNextScreen()
    }

    companion object {
        const val SPLASH_VISIBLE_DURATION_IN_MS = 1000L
    }
}