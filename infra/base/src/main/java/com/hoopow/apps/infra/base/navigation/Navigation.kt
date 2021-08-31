package com.hoopow.apps.infra.base.navigation

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Navigation {
    private val _navigateTo = MutableStateFlow<NavDirections?>(null)
    val navigateTo: StateFlow<NavDirections?> = _navigateTo

    private val _navigateBack = MutableStateFlow(false)
    val navigateBack: StateFlow<Boolean> = _navigateBack

    fun navigate(directions: NavDirections) {
        _navigateTo.value = directions
    }

    fun navigateBack() {
        _navigateBack.value = true
    }

    fun onNavigated() {
        _navigateTo.value = null
        _navigateBack.value = false
    }
}