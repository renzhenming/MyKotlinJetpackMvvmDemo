package com.rzm.jetpackmvvm.base.activity

import android.view.View
import androidx.viewbinding.ViewBinding
import com.rzm.jetpackmvvm.base.ext.inflateBindingWithGeneric
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel

abstract class AbstractMvvmVbActivity<VM : AbstractViewModel, VB : ViewBinding> :
    AbstractMvvmActivity<VM>() {

    override fun layoutId(): Int = 0

    lateinit var mViewBind: VB

    override fun initBinding(): View? {
        mViewBind = inflateBindingWithGeneric(layoutInflater)
        return mViewBind.root
    }
}