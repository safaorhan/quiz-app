package com.hoopow.apps.auth

import android.content.Context
import android.content.res.Resources
import com.amplifyframework.auth.AuthException
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.category.CategoryType.AUTH
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.ByteArrayInputStream

class AuthWorkerTest {
    @Test
    fun `should configure amplify on application create`() {

        val fakeInputStream = ByteArrayInputStream("{}".toByteArray())

        val mockResources = mock<Resources> {
            on { getIdentifier(any(), any(), any()) } doReturn 0
            on { openRawResource(any()) } doReturn fakeInputStream
        }
        val mockContext = mock<Context> {
            on { resources } doReturn mockResources
        }

        val worker = AuthWorker()

        // We expect a configuration exception since the data provided is an empty json object
        val exception = assertThrows(AuthException::class.java) {
            worker.onApplicationCreate(mockContext)
        }

        assertThat(exception.message)
            .isEqualTo("Failed to instantiate AWSMobileClient")

        assertThat(exception.cause?.message)
            .isEqualTo("Failed to initialize AWSMobileClient; please check your awsconfiguration.json")

    }

    @Test
    @Suppress("UsePropertyAccessSyntax")
    fun `should add auth plugin to amplify`() {
        val worker = AuthWorker()

        try {
            worker.onApplicationCreate(mock())
        } catch (_: Exception) {
            // No op
        }

        assertThat(Amplify.getCategoriesMap()[AUTH]?.plugins)
            .isNotEmpty()
    }
}