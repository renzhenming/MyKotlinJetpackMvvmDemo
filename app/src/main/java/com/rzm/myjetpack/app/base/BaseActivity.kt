package com.rzm.myjetpack.app.base

import androidx.databinding.ViewDataBinding
import com.rzm.jetpackmvvm.base.activity.AbstractMvvmDbActivity
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel
import com.rzm.myjetpack.ext.dismissDialog
import com.rzm.myjetpack.ext.showDialog

abstract class BaseActivity<VM : AbstractViewModel, DB : ViewDataBinding> :
    AbstractMvvmDbActivity<VM, DB>() {

    override fun createObserver() {

    }

    override fun showLoadingDialog(it: String) {
        showDialog(it)
    }

    override fun dismissLoadingDialog() {
        dismissDialog()
    }
}