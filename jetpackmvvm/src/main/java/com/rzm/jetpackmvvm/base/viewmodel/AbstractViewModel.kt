package com.rzm.jetpackmvvm.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class AbstractViewModel : ViewModel() {

    val loadingChange by lazy { UiLoadingChange() }

    inner class UiLoadingChange {
        val showDialog by lazy { MutableLiveData<String>() }
        val dismissDialog by lazy { MutableLiveData<Boolean>() }
    }
}