package com.hoopow.apps.infra.base

import androidx.annotation.StringRes
import com.hoopow.apps.infra.base.AsyncResult.Error
import com.hoopow.apps.infra.base.AsyncResult.Loading
import com.hoopow.apps.infra.base.AsyncResult.Success
import com.hoopow.apps.infra.base.ErrorType.DisplayableError
import kotlinx.coroutines.flow.FlowCollector

suspend fun <T> FlowCollector<AsyncResult<T>>.emitSuccess(payload: T) = emit(Success(payload))

suspend fun <T> FlowCollector<AsyncResult<T>>.emitLoading() = emit(Loading())

suspend fun <T> FlowCollector<AsyncResult<T>>.emitError(@StringRes message: Int) = emit(
    Error(
        message = message,
        type = DisplayableError
    )
)

suspend fun <T> FlowCollector<AsyncResult<T>>.emitError(type: ErrorType) = emit(
    Error(
        type = type
    )
)