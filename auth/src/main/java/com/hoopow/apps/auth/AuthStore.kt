package com.hoopow.apps.auth

import java.io.IOException

interface AuthStore {

    suspend fun isSignedIn(): Boolean

    @Throws(AuthExpiredException::class)
    suspend fun fetchUsername(): String

    @Throws(AuthExpiredException::class)
    suspend fun fetchJwt(): String

    suspend fun signUp(params: SignUpParams): Boolean

    suspend fun signIn(params: SignInParams): Boolean

    suspend fun signOut()

    object AuthExpiredException : IOException("There is a problem with your authentication")
}