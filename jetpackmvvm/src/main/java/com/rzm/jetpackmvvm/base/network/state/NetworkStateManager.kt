package com.rzm.jetpackmvvm.base.network.state

import androidx.lifecycle.MutableLiveData

class NetworkStateManager private constructor() {

    val networkStateCallback = MutableLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { NetworkStateManager() }
    }
}