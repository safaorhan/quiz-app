package com.hoopow.apps.test

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.net.HttpURLConnection

fun MockWebServer.givenRespondsSuccess(fileName: String) {
    val response = MockResponse().apply {
        setResponseCode(HttpURLConnection.HTTP_OK)
        setBody(Utils.readTestResourceFile(fileName))
    }

    enqueue(response)
}

fun MockWebServer.givenRespondsError(code: Int) {
    val response = MockResponse().apply {
        setResponseCode(code)
        setBody("401 Unauthorized")
    }

    enqueue(response)
}

private object Utils {
    fun readTestResourceFile(fileName: String) =
        javaClass.classLoader?.getResourceAsStream(fileName)?.bufferedReader()?.readText() ?: ""
}
