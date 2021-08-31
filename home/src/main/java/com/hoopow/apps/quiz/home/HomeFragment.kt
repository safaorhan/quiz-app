package com.hoopow.apps.quiz.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hoopow.apps.infra.base.BaseFragment
import com.hoopow.apps.quiz.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.enableAdjustResizeForTranslucentTheme()
    }
}