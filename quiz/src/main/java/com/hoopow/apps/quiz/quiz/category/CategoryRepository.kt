package com.hoopow.apps.quiz.quiz.category

import com.hoopow.apps.infra.base.AsyncResult
import com.hoopow.apps.infra.base.ErrorType.TokenExpiredError
import com.hoopow.apps.infra.base.emitError
import com.hoopow.apps.infra.base.emitLoading
import com.hoopow.apps.infra.base.emitSuccess
import com.hoopow.apps.quiz.api.Api
import com.hoopow.apps.quiz.quiz.R.string
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: Api
) {
    fun fetchCategories() = flow<AsyncResult<List<Category>>> {
        emitLoading()

        try {
            val response = api.fetchCategories()

            when (response.code()) {
                200 -> emitSuccess(response.body()?.categories.orEmpty().toCategoryList())
                401 -> emitError(TokenExpiredError)
                else -> emitError(string.unexpected_error_occurred)
            }
        } catch (ex: IOException) {
            emitError(string.check_connectivity)
        }
    }
}