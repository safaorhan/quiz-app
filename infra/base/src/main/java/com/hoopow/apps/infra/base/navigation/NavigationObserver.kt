package com.hoopow.apps.infra.base.navigation

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect

class NavigationObserver {
    fun observe(
        navigation: Navigation,
        lifecycleScope: LifecycleCoroutineScope,
        navController: NavController
    ) {

        lifecycleScope.launchWhenStarted {
            navigation.navigateBack.collect { goBack ->
                if (goBack) {
                    navController.popBackStack()
                    navigation.onNavigated()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            navigation.navigateTo.collect { directions ->
                directions?.let {
                    navController.navigate(it)
                    navigation.onNavigated()
                }
            }
        }
    }
}
