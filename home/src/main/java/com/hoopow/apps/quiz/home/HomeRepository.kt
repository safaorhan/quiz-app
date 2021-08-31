package com.hoopow.apps.quiz.home

import com.hoopow.apps.auth.AuthStore
import com.hoopow.apps.infra.base.AsyncResult
import com.hoopow.apps.infra.base.ErrorType.TokenExpiredError
import com.hoopow.apps.infra.base.emitError
import com.hoopow.apps.infra.base.emitLoading
import com.hoopow.apps.infra.base.emitSuccess
import com.hoopow.apps.quiz.api.Api
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val authStore: AuthStore,
    private val api: Api
) {

    fun fetchUsername() = flow<AsyncResult<String>> {
        emitLoading()
        emitSuccess(authStore.fetchUsername())
    }

    fun timeTrialHighScore() = flow<AsyncResult<Int?>> {
        emitLoading()

        val response = api.scoreByUser()

        when (response.code()) {
            200 -> emitSuccess(response.body()?.maxByOrNull { it.score }?.score)
            401 -> emitError(TokenExpiredError)
            else -> emitError(R.string.unexpected_error_occurred)
        }
    }
}