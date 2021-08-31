package com.hoopow.apps.signin

import androidx.navigation.NavDirections
import app.cash.turbine.test
import com.hoopow.apps.auth.AuthStore
import com.hoopow.apps.auth.SignInParams
import com.hoopow.apps.infra.base.AsyncResult.Loading
import com.hoopow.apps.test.CoroutinesTestRule
import com.hoopow.apps.ui.components.PopupView.Type.ERROR
import com.hoopow.apps.ui.components.PopupView.Type.LOADING
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class SignInViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `given there's a sign-in error, should display it`() = runBlocking {
        val authStore = mock<AuthStore> {
            onBlocking { isSignedIn() } doReturn false
            onBlocking { signIn(any()) } doAnswer {
                throw Exception("Sign in failed")
            }
        }

        val viewModel = testViewModel(authStore)

        viewModel.popupViewState.test {
            viewModel.onSignInButtonClick()

            expectItem() // loading state
            val errorState = expectItem()

            assertThat(errorState.type).isEqualTo(ERROR)
            assertThat(errorState.message).isEqualTo(R.string.bad_credentials)
        }
    }

    @Test
    fun `given there's an unexpected error, should display it`() = runBlocking {
        val authStore = mock<AuthStore> {
            onBlocking { isSignedIn() } doReturn false
            onBlocking { signIn(any()) } doAnswer {
                throw Exception("FMDJNFJN!!!")
            }
        }

        val viewModel = testViewModel(authStore)

        viewModel.popupViewState.test {
            viewModel.onSignInButtonClick()

            expectItem() // loading state
            val errorState = expectItem()

            assertThat(errorState.type).isEqualTo(ERROR)
            assertThat(errorState.message).isEqualTo(R.string.unexpected_error_occurred)
        }
    }

    @Test
    fun `given one of the fields are empty, should display error`() = runBlocking {
        val authStore = mock<AuthStore> {
            onBlocking { isSignedIn() } doReturn false
            onBlocking { signIn(any()) } doAnswer {
                throw Exception("One or more parameters are incorrect.")
            }
        }

        val viewModel = testViewModel(authStore)

        viewModel.popupViewState.test {
            viewModel.onSignInButtonClick()

            expectItem() // loading state
            val errorState = expectItem()

            assertThat(errorState.type).isEqualTo(ERROR)
            assertThat(errorState.message).isEqualTo(R.string.cannot_be_empty)
        }
    }

    @Test
    fun `given sign in successful, should navigate to next screen`() = runBlocking {
        val authStore = mock<AuthStore> {
            onBlocking { isSignedIn() } doReturn false
            onBlocking { signIn(any()) } doAnswer {
                val (email, password) = it.arguments[0] as SignInParams

                email == "safa@test.com" && password == "12345678"
            }
        }

        val viewModel = testViewModel(authStore).apply {
            email.value = "safa@test.com"
            password.value = "12345678"
        }

        viewModel.navigation.navigateTo.test {
            val initialDirection = expectItem()
            assertThat(initialDirection).isNull()

            viewModel.onSignInButtonClick()

            val direction = expectItem() as NavDirections
            assertThat(direction.actionId).isEqualTo(R.id.sign_in_successful)
        }
    }

    @Test
    fun `given already signed in, should navigate to next screen`() = runBlocking {
        val authStore = mock<AuthStore> {
            onBlocking { isSignedIn() } doReturn true
        }

        val viewModel = testViewModel(authStore)

        viewModel.navigation.navigateTo.test {
            val direction = expectItem() as NavDirections
            assertThat(direction.actionId).isEqualTo(R.id.sign_in_successful)
        }
    }

    @Test
    fun `should show loading popup when sign in clicked`() = runBlocking {
        val authStore = mock<AuthStore> {
            onBlocking { signIn(any()) } doReturn true
            onBlocking { isSignedIn() } doReturn false
        }

        val viewModel = testViewModel(authStore)

        viewModel.isPopupVisible.test {
            assertThat(expectItem()).isFalse
            viewModel.onSignInButtonClick()
            assertThat(expectItem()).isTrue
        }

        viewModel.popupViewState.test {
            val loadingState = expectItem()
            assertThat(loadingState.type).isEqualTo(LOADING)
            assertThat(loadingState.message).isEqualTo(R.string.loading)
        }
    }

    @Test
    fun `should hide the error popup when it's clicked`() = runBlocking {
        val authStore = mock<AuthStore> {
            onBlocking { signIn(any()) } doReturn false
            onBlocking { isSignedIn() } doReturn false
        }

        val viewModel = testViewModel(authStore)

        viewModel.isPopupVisible.test {
            assertThat(expectItem()).isFalse // initial state

            viewModel.onSignInButtonClick() // causes an error to be displayed
            assertThat(expectItem()).isTrue

            viewModel.onPopupClick()
            assertThat(expectItem()).isFalse // popup disappears
        }
    }

    @Test
    fun `should not hide the loading popup when it's clicked`() = runBlocking {

        val neverEndingRepository = mock<SignInRepository> {
            onBlocking { isSignedIn() } doReturn false
            onBlocking { signIn(any(), any()) } doAnswer {
                flowOf(Loading())
            }
        }
        val viewModel = testViewModel(authStore = mock(), repository = neverEndingRepository)

        viewModel.isPopupVisible.test {
            assertThat(expectItem()).isFalse // initial state

            viewModel.onSignInButtonClick() // causes loading popup to show
            assertThat(expectItem()).isTrue

            viewModel.onPopupClick()
            expectNoEvents() // should not go away
        }
    }

    fun testViewModel(
        authStore: AuthStore,
        repository: SignInRepository = SignInRepository(
            authStore
        )
    ) = SignInViewModel(
        repository
    )
}