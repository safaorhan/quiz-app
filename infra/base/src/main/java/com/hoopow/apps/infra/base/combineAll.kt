package com.hoopow.apps.infra.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

fun <T> List<Flow<T>>.combineAll(): Flow<List<T>> {
    if (this.isEmpty()) return flowOf(emptyList())
    if (this.size == 1) return this[0].map { listOf(it) }
    if (this.size == 2) return this[0].combine(this[1]) { x, y -> listOf(x, y) }

    val initial = this[0].combine(this[1]) { x, y -> listOf(x, y) }

    return drop(2).fold(initial) { acc, flow ->
        acc.combine(flow) { l, z -> l + z }
    }
}