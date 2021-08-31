package com.hoopow.apps.quiz.api

import com.hoopow.apps.auth.AuthStore
import com.hoopow.apps.auth.AuthStore.AuthExpiredException
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val authStore: AuthStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = runBlocking { authStore.fetchJwt() }

        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $jwt")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking { authStore.signOut() }
            throw AuthExpiredException
        }

        return response
    }
}