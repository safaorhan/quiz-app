package com.hoopow.apps.auth

import com.hoopow.apps.infra.worker.ApplicationWorker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface AuthWorkerModule {
    @Binds
    @IntoSet
    fun bindAuthWorker(authWorker: AuthWorker): ApplicationWorker
}