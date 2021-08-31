package com.hoopow.apps.auth

import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.options.AuthSignInOptions
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.kotlin.auth.Auth
import com.hoopow.apps.auth.AuthStore.AuthExpiredException

class AuthStoreImpl(private val auth: Auth) : AuthStore {

    override suspend fun isSignedIn() = auth.fetchAuthSession().isSignedIn

    override suspend fun fetchUsername(): String {
        try {
            return auth.fetchUserAttributes().find {
                it.key == AuthUserAttributeKey.name()
            }!!.value
        } catch (ex: AuthException) {
            throw AuthExpiredException
        }
    }

    override suspend fun fetchJwt(): String {
        try {
            return (auth.fetchAuthSession() as AWSCognitoAuthSession)
                .userPoolTokens
                .value!!
                .idToken
        } catch (ex: AuthException) {
            throw AuthExpiredException
        } catch (ex: java.lang.NullPointerException) {
            throw AuthExpiredException
        }
    }

    override suspend fun signUp(params: SignUpParams): Boolean {
        val options = AuthSignUpOptions.builder()
            .userAttributes(
                listOf(
                    AuthUserAttribute(AuthUserAttributeKey.email(), params.email),
                    AuthUserAttribute(AuthUserAttributeKey.name(), params.displayName)
                )
            )
            .build()

        return auth.signUp(
            username = params.email,
            password = params.password,
            options = options
        ).isSignUpComplete
    }

    override suspend fun signIn(params: SignInParams): Boolean = auth.signIn(
        username = params.email,
        password = params.password,
        options = AuthSignInOptions.defaults()
    ).isSignInComplete

    override suspend fun signOut() = auth.signOut()
}