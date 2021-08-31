package com.hoopow.apps.quiz.home

import androidx.lifecycle.viewModelScope
import androidx.navigation.ActionOnlyNavDirections
import com.hoopow.apps.infra.base.AsyncResult.Error
import com.hoopow.apps.infra.base.AsyncResult.Loading
import com.hoopow.apps.infra.base.BaseViewModel
import com.hoopow.apps.infra.base.ErrorType.TokenExpiredError
import com.hoopow.apps.infra.base.combineAll
import com.hoopow.apps.ui.components.PopupView
import com.hoopow.apps.ui.components.PopupView.Type.ERROR
import com.hoopow.apps.ui.components.PopupView.Type.LOADING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: HomeRepository
) : BaseViewModel() {

    val usernameState = repository.fetchUsername().toStateFlow(Loading())
    val timeTrialHighScoreState = repository.timeTrialHighScore().toStateFlow(Loading())

    val isPopupVisible = listOf(usernameState, timeTrialHighScoreState)
        .combineAll()
        .map { combinedState ->
            combinedState.any { it.isLoading() || it.isDisplayableError() }
        }
        .toStateFlow(true)

    private val loadingState = PopupView.State(LOADING, R.string.loading)
    val popupState = listOf(usernameState, timeTrialHighScoreState)
        .combineAll()
        .map { combinedState ->
            val error = combinedState.find { it.isDisplayableError() } as? Error<*>
            if (error == null) {
                loadingState
            } else {
                PopupView.State(ERROR, error.message)
            }
        }
        .toStateFlow(loadingState)

    init {
        viewModelScope.launch {
            timeTrialHighScoreState.collect {
                if (it is Error<*> && it.type == TokenExpiredError) {
                    navigateToSignIn()
                }
            }
        }
    }

    fun onTimeTrialCardClick() = navigateToTimeTrial()

    private fun navigateToSignIn() =
        navigation.navigate(ActionOnlyNavDirections(R.id.sign_in_required))

    private fun navigateToTimeTrial() =
        navigation.navigate(ActionOnlyNavDirections(R.id.open_time_trial))
}