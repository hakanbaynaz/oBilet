package com.example.obiletcasestudy.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    val viewBinding get() = _viewBinding!!

    private var _viewBinding: T? = null

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): T

    abstract fun T.onBindView(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = getBinding(inflater, container)
        viewBinding.onBindView(savedInstanceState)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}