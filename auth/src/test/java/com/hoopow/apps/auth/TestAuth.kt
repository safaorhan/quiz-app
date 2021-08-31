package com.hoopow.apps.auth

import android.app.Activity
import android.content.Intent
import com.amplifyframework.auth.AuthCodeDeliveryDetails
import com.amplifyframework.auth.AuthDevice
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.AuthSession
import com.amplifyframework.auth.AuthUser
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthConfirmSignInOptions
import com.amplifyframework.auth.options.AuthSignInOptions
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.options.AuthWebUISignInOptions
import com.amplifyframework.auth.result.AuthResetPasswordResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.result.AuthSignUpResult
import com.amplifyframework.auth.result.AuthUpdateAttributeResult
import com.amplifyframework.kotlin.auth.Auth

open class TestAuth : Auth{
    override suspend fun confirmResetPassword(newPassword: String, confirmationCode: String) {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun confirmSignIn(
        confirmationCode: String,
        options: AuthConfirmSignInOptions
    ): AuthSignInResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun confirmSignUp(
        username: String,
        confirmationCode: String
    ): AuthSignUpResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun confirmUserAttribute(
        attributeKey: AuthUserAttributeKey,
        confirmationCode: String
    ) {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun fetchAuthSession(): AuthSession {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun fetchDevices(): List<AuthDevice> {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun fetchUserAttributes(): List<AuthUserAttribute> {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun forgetDevice(device: AuthDevice?) {
        throw NotImplementedError("An operation is not implemented")
    }

    override fun getCurrentUser(): AuthUser? {
        throw NotImplementedError("An operation is not implemented")
    }

    override fun handleWebUISignInResponse(intent: Intent) {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun rememberDevice() {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun resendSignUpCode(username: String): AuthSignUpResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun resendUserAttributeConfirmationCode(attributeKey: AuthUserAttributeKey): AuthCodeDeliveryDetails {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun resetPassword(username: String): AuthResetPasswordResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun signIn(
        username: String?,
        password: String?,
        options: AuthSignInOptions
    ): AuthSignInResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun signInWithSocialWebUI(
        provider: AuthProvider,
        callingActivity: Activity,
        options: AuthWebUISignInOptions
    ): AuthSignInResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun signInWithWebUI(
        callingActivity: Activity,
        options: AuthWebUISignInOptions
    ): AuthSignInResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun signOut(options: AuthSignOutOptions) {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun signUp(
        username: String,
        password: String,
        options: AuthSignUpOptions
    ): AuthSignUpResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun updatePassword(oldPassword: String, newPassword: String) {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun updateUserAttribute(attribute: AuthUserAttribute): AuthUpdateAttributeResult {
        throw NotImplementedError("An operation is not implemented")
    }

    override suspend fun updateUserAttributes(attributes: List<AuthUserAttribute>): Map<AuthUserAttributeKey, AuthUpdateAttributeResult> {
        throw NotImplementedError("An operation is not implemented")
    }
}