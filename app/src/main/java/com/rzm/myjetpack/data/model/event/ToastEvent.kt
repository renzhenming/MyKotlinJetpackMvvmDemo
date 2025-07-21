package com.rzm.myjetpack.data.model.event

sealed class ToastEvent {
    /**
     * 用户名输入为空
     */
    object UsernameEmpty : ToastEvent()
    /**
     * 密码输入为空
     */
    object PasswordEmpty : ToastEvent()
}