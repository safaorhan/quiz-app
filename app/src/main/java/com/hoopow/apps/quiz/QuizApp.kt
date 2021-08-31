package com.hoopow.apps.quiz

import android.app.Application
import com.hoopow.apps.infra.worker.ApplicationWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class QuizApp : Application() {
    @Inject
    lateinit var applicationWorkers : Set<@JvmSuppressWildcards ApplicationWorker>

    override fun onCreate() {
        super.onCreate()

        applicationWorkers.forEach { it.onApplicationCreate(this) }
    }
}