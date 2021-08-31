package com.hoopow.apps.quiz.api

import com.hoopow.apps.auth.AuthStore
import com.hoopow.apps.auth.SignInParams
import com.hoopow.apps.auth.SignUpParams

open class TestAuthStore : AuthStore {
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