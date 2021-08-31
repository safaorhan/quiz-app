package com.hoopow.apps.quiz.quiz.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.hoopow.apps.infra.base.BaseFragment
import com.hoopow.apps.quiz.quiz.QuizConfigViewModel
import com.hoopow.apps.quiz.quiz.R.layout
import com.hoopow.apps.quiz.quiz.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(
    layout.fragment_category
) {
    override val viewModel by activityViewModels<QuizConfigViewModel>()

    @Inject
    lateinit var categoryItemDecoration: CategoryItemDecoration

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryAdapter = CategoryAdapter(viewModel)
        binding.categoryItemDecoration = categoryItemDecoration
    }
}