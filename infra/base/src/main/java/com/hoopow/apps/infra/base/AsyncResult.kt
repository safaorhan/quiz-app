package com.hoopow.apps.infra.base

import androidx.annotation.StringRes
import com.hoopow.apps.infra.base.ErrorType.DisplayableError

sealed class AsyncResult<T> {
    class Loading<T> : AsyncResult<T>()
    class Success<T>(val payload: T) : AsyncResult<T>()
    class Error<T>(
        @StringRes val message: Int = 0,
        val type: ErrorType = DisplayableError
    ) : AsyncResult<T>()

    fun isLoading() = this is Loading<*>

    fun isSuccess() = this is Success<*>

    fun isError() = this is Error<*>

    fun isDisplayableError() = this is Error<*> && this.type == DisplayableError

    fun value(): T? = when (this) {
        is Success<T> -> this.payload
        else -> null
    }

    companion object {
        fun AsyncResult<*>.errorMessage() = (this as Error).message
    }
}

enum class ErrorType {
    DisplayableError,
    TokenExpiredError
}
