package com.hoopow.apps.signin

import androidx.lifecycle.viewModelScope
import androidx.navigation.ActionOnlyNavDirections
import com.hoopow.apps.infra.base.AsyncResult.Error
import com.hoopow.apps.infra.base.AsyncResult.Loading
import com.hoopow.apps.infra.base.AsyncResult.Success
import com.hoopow.apps.infra.base.BaseViewModel
import com.hoopow.apps.infra.base.ErrorType.DisplayableError
import com.hoopow.apps.ui.components.PopupView
import com.hoopow.apps.ui.components.PopupView.Type.ERROR
import com.hoopow.apps.ui.components.PopupView.Type.LOADING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: SignInRepository
) : BaseViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    val isPopupVisible = stateFlow(false)

    private val loadingState = PopupView.State(type = LOADING, message = R.string.loading)

    val popupViewState = stateFlow(loadingState)

    init {
        viewModelScope.launch {
            if (repository.isSignedIn()) {
                navigateToNextScreen()
            }
        }
    }

    fun onSignInButtonClick() = viewModelScope.launch {
        repository.signIn(email.value, password.value).collect {
            when (it) {
                is Loading -> showLoadingPopup()
                is Success -> navigateToNextScreen()
                is Error -> when (it.type) {
                    DisplayableError -> showErrorPopup(it.message)
                    else -> throw IllegalArgumentException("")
                }
            }
        }
    }

    fun onPopupClick() {
        if (popupViewState.value.type == ERROR) {
            isPopupVisible.setValue(false)
        }
    }

    private fun showErrorPopup(message: Int) = popupViewState.setValue(
        PopupView.State(type = ERROR, message = message)
    ).also {
        isPopupVisible.setValue(true)
    }

    private fun showLoadingPopup() = popupViewState.setValue(loadingState).also {
        isPopupVisible.setValue(true)
    }

    private fun navigateToNextScreen() {
        navigation.navigate(ActionOnlyNavDirections(R.id.sign_in_successful))
    }
}