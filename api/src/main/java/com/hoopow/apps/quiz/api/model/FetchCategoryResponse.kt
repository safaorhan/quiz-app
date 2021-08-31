package com.hoopow.apps.quiz.api.model

import com.google.gson.annotations.SerializedName

data class FetchCategoryResponse(
    @SerializedName("category")
    val categories: List<CategoryResponse>
)
