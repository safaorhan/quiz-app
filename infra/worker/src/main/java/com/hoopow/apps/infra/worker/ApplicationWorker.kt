package com.hoopow.apps.infra.worker

import android.content.Context

interface ApplicationWorker {
    fun onApplicationCreate(applicationContext: Context)
}