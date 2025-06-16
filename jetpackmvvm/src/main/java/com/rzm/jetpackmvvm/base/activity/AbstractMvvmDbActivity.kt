package com.rzm.jetpackmvvm.base.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import com.rzm.jetpackmvvm.base.ext.inflateBindingWithGeneric
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel

abstract class AbstractMvvmDbActivity<VM : AbstractViewModel, DB : ViewDataBinding> :
    AbstractMvvmActivity<VM>() {

    lateinit var mDatabind: DB

    override fun layoutId(): Int {
        return 0
    }

    override fun initBinding(): View? {
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        return mDatabind.root
    }
}