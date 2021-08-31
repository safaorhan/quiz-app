package com.hoopow.apps.test

import com.hoopow.apps.quiz.api.Api
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun testApi(server: MockWebServer) = Retrofit.Builder()
    .client(OkHttpClient.Builder().build())
    .baseUrl(server.url("/"))
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Api::class.java)

