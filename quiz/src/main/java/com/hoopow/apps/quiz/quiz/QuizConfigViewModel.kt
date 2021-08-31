package com.hoopow.apps.quiz.quiz

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import androidx.navigation.ActionOnlyNavDirections
import com.hoopow.apps.infra.base.AsyncResult.Companion.errorMessage
import com.hoopow.apps.infra.base.AsyncResult.Success
import com.hoopow.apps.infra.base.BaseViewModel
import com.hoopow.apps.quiz.quiz.category.Category
import com.hoopow.apps.quiz.quiz.category.CategoryRepository
import com.hoopow.apps.quiz.quiz.config.Environment.GRAND_LAGOON
import com.hoopow.apps.quiz.quiz.config.QuizConfig
import com.hoopow.apps.quiz.quiz.config.Ship.THE_LITTLE_SAILBOAT
import com.hoopow.apps.ui.components.PopupView
import com.hoopow.apps.ui.components.PopupView.Type.ERROR
import com.hoopow.apps.ui.components.PopupView.Type.LOADING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizConfigViewModel @Inject constructor(repository: CategoryRepository) : BaseViewModel() {

    private val fetchCategoriesFlow = repository.fetchCategories().toSharedFlow()

    val config = stateFlow(initialConfig)

    val categories = fetchCategoriesFlow.map {
        when (it) {
            is Success<List<Category>> -> it.payload
            else -> emptyList()
        }
    }.toStateFlow(emptyList())

    val isPopupVisible = fetchCategoriesFlow.map {
        it.isLoading() || it.isDisplayableError()
    }.toStateFlow(false)

    val popupState = fetchCategoriesFlow.map {
        when {
            it.isDisplayableError() -> errorState(it.errorMessage())
            else -> loadingState
        }
    }.toStateFlow(loadingState)

    init {
        fetchCategoriesAndInitializeConfig()
    }

    private fun fetchCategoriesAndInitializeConfig() = viewModelScope.launch {
        fetchCategoriesFlow.filter {
            it is Success<*>
        }.collect {
            config.setValue(
                config.value.copy(
                    categoryId = (it as Success).payload[0].id
                )
            )
        }
    }

    fun onCategoryClick(category: Category) {
        config.setValue(
            config.value.copy(
                categoryId = category.id
            )
        )

        navigateToNextScreen()
    }

    fun onRandomButtonClick() = onCategoryClick(categories.value.random())

    fun onBackButtonClick() = navigation.navigateBack()

    private fun navigateToNextScreen() {
        val directions = ActionOnlyNavDirections(R.id.go_to_next_screen)
        navigation.navigate(directions)
    }

    companion object {
        val initialConfig = QuizConfig(
            categoryId = "",
            environment = GRAND_LAGOON,
            ship = THE_LITTLE_SAILBOAT
        )

        val loadingState = PopupView.State(LOADING, R.string.loading)

        fun errorState(@StringRes message: Int) = PopupView.State(ERROR, message)
    }
}