package com.hoopow.apps.quiz.home

import androidx.navigation.NavDirections
import app.cash.turbine.test
import com.hoopow.apps.auth.AuthStore
import com.hoopow.apps.infra.base.AsyncResult.Loading
import com.hoopow.apps.infra.base.AsyncResult.Success
import com.hoopow.apps.test.CoroutinesTestRule
import com.hoopow.apps.test.givenRespondsError
import com.hoopow.apps.test.givenRespondsSuccess
import com.hoopow.apps.test.testApi
import com.hoopow.apps.ui.components.PopupView
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class HomeViewModelTest {

    @get:Rule
    val server = MockWebServer()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val signedInAuthStore = mock<AuthStore> {
        onBlocking { fetchUsername() } doReturn "Safa Orhan"
    }

    @Test
    fun `given server returns successful response, should display highscore`() {
        server.givenRespondsSuccess("score-by-user-success.json")

        val viewModel = testViewModel(server, signedInAuthStore)

        runBlocking {
            viewModel.timeTrialHighScoreState.test {
                val initialState = expectItem()
                assertThat(initialState).isInstanceOf(Loading::class.java)

                val nextState = expectItem()
                assertThat(nextState).isInstanceOf(Success::class.java)
                assertThat((nextState as Success).payload).isEqualTo(424242)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `given server responds with 401, should navigate to login`() {
        server.givenRespondsError(401)

        val viewModel = testViewModel(server, signedInAuthStore)

        runBlocking {
            viewModel.navigation.navigateTo.test {
                val initialState = expectItem()
                assertThat(initialState).isNull()

                val nextState = expectItem()
                assertThat((nextState as NavDirections).actionId).isEqualTo(R.id.sign_in_required)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `given server responds with unexpected error, should display error popup`() {
        server.givenRespondsError(500)

        val viewModel = testViewModel(server, signedInAuthStore)

        runBlocking {

            viewModel.isPopupVisible.test {
                val state = expectItem()

                assertThat(state).isTrue()
            }

            viewModel.popupState.test {
                val errorState = expectItem()

                assertThat(errorState.type).isEqualTo(PopupView.Type.ERROR)
                assertThat(errorState.message).isEqualTo(R.string.unexpected_error_occurred)
            }
        }
    }

    @Test
    fun `given successful response, loading popup should initially show and then hide`() {
        server.givenRespondsSuccess("score-by-user-success.json")

        val viewModel = testViewModel(server, signedInAuthStore)

        runBlocking {

            viewModel.isPopupVisible.test {
                val initialState = expectItem()

                val nextState = expectItem()


                assertThat(initialState).isTrue
                assertThat(nextState).isFalse

                cancelAndIgnoreRemainingEvents()
            }

            viewModel.popupState.test {
                val state = expectItem()

                assertThat(state.type).isEqualTo(PopupView.Type.LOADING)
                assertThat(state.message).isEqualTo(R.string.loading)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    fun testViewModel(
        server: MockWebServer,
        authStore: AuthStore
    ) = HomeViewModel(
        HomeRepository(
            authStore = authStore,
            api = testApi(server)
        )
    )

}