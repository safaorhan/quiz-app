package com.hoopow.apps.infra.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoopow.apps.infra.base.navigation.Navigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

abstract class BaseViewModel : ViewModel() {
    val navigation = Navigation()

    protected fun <T> Flow<T>.toSharedFlow() = shareIn(viewModelScope, SharingStarted.Lazily)

    protected fun <T> Flow<T>.toStateFlow(initial: T) =
        stateIn(viewModelScope, SharingStarted.Lazily, initial)

    protected fun <T, R> StateFlow<T>.mapTo(initial: R, transformation: suspend (T) -> R) =
        map(transformation).toStateFlow(initial)

    protected fun <T> stateFlow(initial: T): HiddenStateFlow<T> = HiddenStateFlowImpl(initial)

    protected fun <T> HiddenStateFlow<T>.setValue(value: T): Unit = when (this) {
        is HiddenStateFlowImpl -> wrapped.value = value
    }
}

sealed class HiddenStateFlow<T>(stateFlow: StateFlow<T>) : StateFlow<T> by stateFlow

private class HiddenStateFlowImpl<T>(
    initial: T,
    val wrapped: MutableStateFlow<T> = MutableStateFlow(initial)
) : HiddenStateFlow<T>(wrapped)
