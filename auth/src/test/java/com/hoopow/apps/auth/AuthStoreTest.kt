package com.hoopow.apps.auth

import com.amplifyframework.auth.AuthSession
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.AWSCognitoUserPoolTokens
import com.amplifyframework.auth.options.AuthSignInOptions
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.auth.result.step.AuthNextSignInStep
import com.amplifyframework.auth.result.step.AuthNextSignUpStep
import com.amplifyframework.auth.result.step.AuthSignInStep
import com.amplifyframework.auth.result.step.AuthSignUpStep.DONE
import com.amplifyframework.kotlin.auth.Auth
import com.hoopow.apps.auth.AuthStore.AuthExpiredException
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AuthStoreTest {

    @Test
    fun `should provide isSignedIn from session when signed in`() {
        // Given
        val expected = true

        val testAuth = object : TestAuth() {
            override suspend fun fetchAuthSession(): AuthSession {
                return AuthSession(expected)
            }
        }

        val authStore = AuthStoreImpl(testAuth)

        // When
        val actual = runBlocking { authStore.isSignedIn() }

        // Then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should provide isSignedIn from session when not signed in`() {
        // Given
        val expected = false

        val testAuth = object : TestAuth() {
            override suspend fun fetchAuthSession(): AuthSession {
                return AuthSession(expected)
            }
        }

        val authStore = AuthStoreImpl(testAuth)

        // When
        val actual = runBlocking { authStore.isSignedIn() }

        // Then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should fetch idToken when fetched jwt whilst signed in`() {
        // Given
        val expectedJwt = "idToken"

        val testAuth = object : TestAuth() {
            override suspend fun fetchAuthSession(): AuthSession {
                return AWSCognitoAuthSession(
                    true,
                    AuthSessionResult.success("identityId"),
                    AuthSessionResult.success(mock()),
                    AuthSessionResult.success("userSub"),
                    AuthSessionResult.success(
                        AWSCognitoUserPoolTokens(
                            "accessToken",
                            "idToken",
                            "refreshToken"
                        )
                    )
                )
            }
        }

        val authStore = AuthStoreImpl(testAuth)

        // When
        val actualJwt = runBlocking { authStore.fetchJwt() }

        // Then
        assertThat(actualJwt).isEqualTo(expectedJwt)
    }

    @Test
    fun `should throw AuthExpiredException if fetched jwt while not signed in`() {
        val testAuth = object : TestAuth() {
            override suspend fun fetchAuthSession(): AuthSession {
                return AWSCognitoAuthSession(
                    false,
                    AuthSessionResult.failure(mock()),
                    AuthSessionResult.failure(mock()),
                    AuthSessionResult.failure(mock()),
                    AuthSessionResult.failure(mock())
                )
            }
        }

        val authStore = AuthStoreImpl(testAuth)

        Assert.assertThrows(AuthExpiredException::class.java) {
            runBlocking { authStore.fetchJwt() }
        }
    }

    @Test
    fun `should sign up with correct params`() {
        val mockAuth = mock<Auth> {
            onBlocking { signUp(any(), any(), any()) } doReturn AuthSignUpResult(
                true, AuthNextSignUpStep(DONE, emptyMap(), null), null
            )
        }

        val authStore = AuthStoreImpl(mockAuth)

        runBlocking {
            authStore.signUp(
                SignUpParams(
                    email = "some_email",
                    password = "some_password",
                    displayName = "some_display_name"
                )
            )

            verify(mockAuth).signUp(
                check {
                    assertThat(it).isEqualTo("some_email")
                }, check {
                    assertThat("some_password")
                }, check {
                    assertThat(it.userAttributes).hasSize(2)
                    assertThat(it.userAttributes).contains(
                        AuthUserAttribute(AuthUserAttributeKey.name(), "some_display_name")
                    )
                    assertThat(it.userAttributes).contains(
                        AuthUserAttribute(AuthUserAttributeKey.email(), "some_email")
                    )
                })
        }
    }

    @Test
    fun `should sign in with correct params`() {
        val mockAuth = mock<Auth> {
            onBlocking { signIn(any(), any(), any()) } doReturn AuthSignInResult(
                true,
                AuthNextSignInStep(AuthSignInStep.DONE, emptyMap(), null)
            )
        }

        val authStore = AuthStoreImpl(mockAuth)

        runBlocking {
            authStore.signIn(
                SignInParams(
                    email = "some_email",
                    password = "some_password"
                )
            )

            verify(mockAuth).signIn(
                check {
                    assertThat(it).isEqualTo("some_email")
                },
                check {
                    assertThat(it).isEqualTo("some_password")
                },
                any()
            )
        }
    }

    @Test
    fun `should return isSignInComplete after signing in`() {
        // Given
        val expected = true

        val testAuth = object : TestAuth() {
            override suspend fun signIn(
                username: String?,
                password: String?,
                options: AuthSignInOptions
            ): AuthSignInResult {
                return AuthSignInResult(
                    expected,
                    AuthNextSignInStep(AuthSignInStep.DONE, emptyMap(), null)
                )
            }
        }

        val authStore = AuthStoreImpl(testAuth)

        val result = runBlocking {
            authStore.signIn(
                SignInParams(
                    email = "some_email",
                    password = "some_password"
                )
            )
        }

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should return isSignUpComplete after signing up`() {
        // Given
        val expected = true

        val testAuth = object : TestAuth() {
            override suspend fun signUp(
                username: String,
                password: String,
                options: AuthSignUpOptions
            ): AuthSignUpResult {
                return AuthSignUpResult(
                    expected,
                    AuthNextSignUpStep(DONE, emptyMap(), null),
                    null
                )
            }
        }

        val authStore = AuthStoreImpl(testAuth)

        val result = runBlocking {
            authStore.signUp(
                SignUpParams(
                    email = "some_email",
                    password = "some_password",
                    displayName = "some_display_name"
                )
            )
        }

        assertThat(result).isEqualTo(expected)
    }
}