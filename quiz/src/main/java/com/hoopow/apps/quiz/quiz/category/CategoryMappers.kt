package com.hoopow.apps.quiz.quiz.category

import com.hoopow.apps.quiz.api.model.CategoryResponse

fun CategoryResponse.toCategory() = Category(id = id, label = label, bestScore = 123, starCount = 2)

fun List<CategoryResponse>.toCategoryList() = map { it.toCategory() }