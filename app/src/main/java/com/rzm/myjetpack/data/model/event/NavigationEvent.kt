package com.rzm.myjetpack.data.model.event

sealed class NavigationEvent {

    /**
     * 去注册页面
     */
    object ToRegister : NavigationEvent()
}