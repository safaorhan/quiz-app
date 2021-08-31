package com.hoopow.apps.auth

import com.amplifyframework.kotlin.core.Amplify
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindsAuthStore(authStore: AuthStoreImpl): AuthStore

    companion object {
        @Provides
        fun providesAuthStoreImpl() = AuthStoreImpl(Amplify.Auth)
    }
}