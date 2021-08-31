package com.hoopow.apps.auth

import android.content.Context
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.kotlin.core.Amplify
import com.hoopow.apps.infra.worker.ApplicationWorker
import javax.inject.Inject

class AuthWorker @Inject constructor() : ApplicationWorker {
    override fun onApplicationCreate(applicationContext: Context) {
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(applicationContext)
    }
}