package com.hoopow.apps.quiz.api

import com.hoopow.apps.quiz.api.model.FetchCategoryResponse
import com.hoopow.apps.quiz.api.model.Score
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("score/byuser")
    suspend fun scoreByUser(): Response<List<Score>>

    @GET("main/first")
    suspend fun fetchCategories(): Response<FetchCategoryResponse>
}