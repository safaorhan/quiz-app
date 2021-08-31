package com.hoopow.apps.infra.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hoopow.apps.infra.base.navigation.NavigationObserver

abstract class BaseFragment<DB : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    Fragment() {

    abstract val viewModel: BaseViewModel

    private val navigationObserver = NavigationObserver()

    protected lateinit var binding: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<DB>(inflater, layoutId, container, false).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        setVariable(BR.viewModel, viewModel)
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationObserver.observe(viewModel.navigation, lifecycleScope, findNavController())
    }

    protected fun View.enableAdjustResizeForTranslucentTheme() {
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            v.fitsSystemWindows = true
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

            val i = WindowInsetsCompat.Builder().setInsets(
                WindowInsetsCompat.Type.systemBars(),
                Insets.of(0, 0, 0, bottom)
            ).build()

            ViewCompat.onApplyWindowInsets(v, i)
        }
    }
}
