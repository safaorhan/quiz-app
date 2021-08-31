package com.hoopow.apps.signin

import com.hoopow.apps.auth.AuthStore
import com.hoopow.apps.auth.SignInParams
import com.hoopow.apps.infra.base.AsyncResult
import com.hoopow.apps.infra.base.OpenForTesting
import com.hoopow.apps.infra.base.emitError
import com.hoopow.apps.infra.base.emitLoading
import com.hoopow.apps.infra.base.emitSuccess
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OpenForTesting
class SignInRepository @Inject constructor(
    private val authStore: AuthStore
) {
    suspend fun signIn(email: String, password: String) = flow<AsyncResult<Unit>> {
        emitLoading()

        val signInComplete = authStore.signIn(SignInParams(email, password))

        if (signInComplete) {
            emitSuccess(Unit)
        } else {
            emitError(R.string.unexpected_error_occurred)
        }
    }.catch {
        val errorMessage = when (it.message) {
            "One or more parameters are incorrect." -> R.string.cannot_be_empty
            "Sign in failed" -> R.string.bad_credentials
            else -> R.string.unexpected_error_occurred
        }

        emitError(errorMessage)
    }

    suspend fun isSignedIn() = authStore.isSignedIn()
}