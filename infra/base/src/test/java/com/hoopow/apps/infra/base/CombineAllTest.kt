package com.hoopow.apps.infra.base

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CombineAllTest {

    @Test
    fun `empty list should emit empty list`() {
        runBlocking {
            emptyList<Flow<Int>>().combineAll().collect {
                assertThat(it).isEqualTo(emptyList<Int>())
            }
        }
    }

    @Test
    fun `list of one item should emit list of that item`() {
        runBlocking {
            listOf(flowOf(1)).combineAll().collect {
                assertThat(it).containsExactly(1)
            }
        }
    }

    @Test
    fun `list of two items should combine correctly`() {
        runBlocking {
            val f1 = flow {
                emit(1)
                delay(1)
                emit(2)
            }

            val f2 = flow {
                emit("a")
                delay(2)
                emit("b")
            }

            val combined = listOf(f1, f2).combineAll()

            assertThat(combined.take(3).toList()).containsExactly(
                listOf(1, "a"),
                listOf(2, "a"),
                listOf(2, "b")
            )
        }
    }

    @Test
    fun `list of three items should combine correctly`() {
        runBlocking {
            val f1 = flow {
                emit(1)
                delay(1)
                emit(2)
            }

            val f2 = flow {
                emit("a")
                delay(2)
                emit("b")
            }

            val f3 = flow {
                delay(3)
                emit("X")
                delay(3)
                emit("Y")
            }

            val combined = listOf(f1, f2, f3).combineAll()

            assertThat(combined.take(4).toList()).containsExactly(
                listOf(1, "a", "X"),
                listOf(2, "a", "X"),
                listOf(2, "b", "X"),
                listOf(2, "b", "Y")
            )
        }
    }

    @Test
    fun `given items are emitting without delay, they should combine correctly`() {
        runBlocking {
            val f1 = flow {
                emit("A")
            }

            val f2 = flow {
                emit("B")
            }

            val f3 = flow {
                emit("C")
            }

            val f4 = flow {
                emit("D")
            }

            val combined = listOf(f1, f2, f3, f4).combineAll()

            assertThat(combined.first()).containsExactly("A", "B", "C", "D")
        }
    }
}