package com.rzm.myjetpack.ui.main.login.viewmodel

import android.view.View
import android.widget.CompoundButton
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rzm.jetpackmvvm.base.ext.request
import com.rzm.jetpackmvvm.base.state.LoadingResult
import com.rzm.myjetpack.app.base.BaseViewModel
import com.rzm.myjetpack.app.network.apiService
import com.rzm.myjetpack.data.model.UserInfo
import com.rzm.myjetpack.data.model.event.NavigationEvent
import com.rzm.myjetpack.data.model.event.ToastEvent
import com.rzm.myjetpack.eventViewModel

class LoginViewModel : BaseViewModel() {

    var username = ObservableField<String>()
    var password = ObservableField<String>()
    var isShowPassword = ObservableField<Boolean>()
    var loginResult = MutableLiveData<LoadingResult<UserInfo>>()

    // 私有 MutableLiveData（仅 ViewModel 内部可修改）
    private val _navigationEvents = MutableLiveData<NavigationEvent>()

    // 公开的 LiveData（仅 View 层可观察，不可修改）
    val navigationEvents: LiveData<NavigationEvent> = _navigationEvents

    //用户名清除按钮是否显示
    var isClearBtnVisible = object : ObservableInt(username) {
        override fun get(): Int {
            return if (username.get()?.isEmpty() == true) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    ///密码显示按钮是否显示
    var isPasswordVisible = object : ObservableInt(password) {
        override fun get(): Int {
            return if (password.get()?.isEmpty() == true) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    fun clear() {
        username.set("")
    }

    fun login() {
        when {
            (username.get() ?: "").isEmpty() -> {
                eventViewModel.toastEvent.value = ToastEvent.UsernameEmpty
            }
            (password.get() ?: "").isEmpty() -> {
                eventViewModel.toastEvent.value = ToastEvent.PasswordEmpty
            }
            else -> login(username.get()!!, password.get()!!)
        }
    }

    fun goRegister() {
        _navigationEvents.value = NavigationEvent.ToRegister
    }

    var onShowPasswordCheckChangeListener =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            isShowPassword.set(isChecked)
        }

    fun login(username: String, password: String) {
        request({ apiService.login(username, password) }, loginResult, true)
    }
}