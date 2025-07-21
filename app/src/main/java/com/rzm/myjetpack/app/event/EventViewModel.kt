package com.rzm.myjetpack.app.event

import androidx.lifecycle.MutableLiveData
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel
import com.rzm.myjetpack.data.model.event.ToastEvent

class EventViewModel : AbstractViewModel() {
    var toastEvent = MutableLiveData<ToastEvent>()
}