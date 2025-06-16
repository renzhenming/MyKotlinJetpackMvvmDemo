package com.rzm.jetpackmvvm.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.rzm.jetpackmvvm.base.ext.inflateBindingWithGeneric
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel

abstract class AbstractMvvmVbFragment<VM : AbstractViewModel, VB : ViewBinding> :
    AbstractMvvmFragment<VM>() {

    override fun layoutId() = 0

    private var _binding: VB? = null
    private val mViewBind: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(inflater, container, false)
        return mViewBind.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}