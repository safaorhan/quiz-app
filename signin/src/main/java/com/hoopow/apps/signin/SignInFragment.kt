package com.hoopow.apps.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hoopow.apps.infra.base.BaseFragment
import com.hoopow.apps.signin.databinding.SignInFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {
    override val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.enableAdjustResizeForTranslucentTheme()
    }
}