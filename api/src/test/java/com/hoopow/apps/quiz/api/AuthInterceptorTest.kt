package com.hoopow.apps.quiz.api

import com.hoopow.apps.auth.AuthStore
import com.hoopow.apps.auth.AuthStore.AuthExpiredException
import com.hoopow.apps.auth.SignInParams
import com.hoopow.apps.auth.SignUpParams
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking

class AuthInterceptorTest {

    private val server = MockWebServer()

    @Test
    fun `when signed in, should append jwt as an authorization header`() {
        val testAuthStore = object : TestAuthStore() {
            override suspend fun fetchJwt() = "SOME_JWT"
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(testAuthStore))
            .build()

        server.enqueue(MockResponse())

        okHttpClient.newCall(Request.Builder().url(server.url("/")).build()).execute()

        val request = server.takeRequest()
        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer SOME_JWT")
    }

    @Test
    fun `when response code is 401, should sign out and throw AuthExpiredException`() {
        val mockAuthStore = mock<AuthStore> {
            onBlocking { fetchJwt() } doReturn "SOME_JWT"
            onBlocking { signOut() } doReturn Unit
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(mockAuthStore))
            .build()

        val response = MockResponse().apply {
            setResponseCode(401)
        }

        server.enqueue(response)

        Assert.assertThrows(AuthExpiredException::class.java) {
            okHttpClient.newCall(Request.Builder().url(server.url("/")).build()).execute()
        }

        verifyBlocking(mockAuthStore) { signOut() }
    }

    fun testAuthStore(isSignedIn: Boolean) = object : AuthStore {
        override suspend fun isSignedIn(): Boolean {
            throw NotImplementedError("An operation is not implemented")
        }

        override suspend fun fetchUsername(): String {
            throw NotImplementedError("An operation is not implemented")
        }

        override suspend fun fetchJwt(): String {
            throw NotImplementedError("An operation is not implemented")
        }

        override suspend fun signUp(params: SignUpParams): Boolean {
            throw NotImplementedError("An operation is not implemented")
        }

        override suspend fun signIn(params: SignInParams): Boolean {
            throw NotImplementedError("An operation is not implemented")
        }

        override suspend fun signOut() {
            throw NotImplementedError("An operation is not implemented")
        }

    }
}