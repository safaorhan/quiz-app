package com.hoopow.apps.quiz.api

import com.hoopow.apps.infra.base.Environment.DEV
import com.hoopow.apps.infra.base.Environment.PROD
import com.hoopow.apps.infra.base.Environment.QA
import com.hoopow.apps.infra.base.asEnvironment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL_DEV =
        "https://REDACTED.amazonaws.com/dev/platform/"
    private const val BASE_URL_QA =
        "https://REDACTED.amazonaws.com/qa/platform/"
    private const val BASE_URL_PROD =
        "https://REDACTED.amazonaws.com/prod/platform/"

    @Provides
    fun providesHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptorIfDebug(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): Api = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrlForDev(BASE_URL_DEV)
        .baseUrlForQa(BASE_URL_QA)
        .baseUrlForProd(BASE_URL_PROD)
        .build()
        .create(Api::class.java)

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = BODY
    }

    private fun OkHttpClient.Builder.addInterceptorIfDebug(interceptor: Interceptor): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            addInterceptor(interceptor)
        }

        return this
    }

    private fun Retrofit.Builder.baseUrlForDev(baseUrl: String): Retrofit.Builder {
        if (BuildConfig.FLAVOR.asEnvironment() == DEV) {
            baseUrl(baseUrl)
        }

        return this
    }

    private fun Retrofit.Builder.baseUrlForQa(baseUrl: String): Retrofit.Builder {
        if (BuildConfig.FLAVOR.asEnvironment() == QA) {
            baseUrl(baseUrl)
        }

        return this
    }

    private fun Retrofit.Builder.baseUrlForProd(baseUrl: String): Retrofit.Builder {
        if (BuildConfig.FLAVOR.asEnvironment() == PROD) {
            baseUrl(baseUrl)
        }

        return this
    }
}