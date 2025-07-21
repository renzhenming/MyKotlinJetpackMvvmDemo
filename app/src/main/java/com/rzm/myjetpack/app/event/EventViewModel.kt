package com.rzm.myjetpack.app.event

import androidx.lifecycle.MutableLiveData
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel
import com.rzm.myjetpack.data.model.event.CollectEvent
import com.rzm.myjetpack.data.model.event.ToastEvent

class EventViewModel : AbstractViewModel() {
    /**
     * toast事件
     */
    var toastEvent = MutableLiveData<ToastEvent>()

    /**
     * 　收藏事件
     */
    val collectEvent = MutableLiveData<CollectEvent>()
}