package com.rzm.myjetpack.ui.main.login.viewmodel

import android.view.View
import android.widget.CompoundButton
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rzm.jetpackmvvm.base.ext.launch
import com.rzm.jetpackmvvm.base.state.LoadingResult
import com.rzm.myjetpack.app.base.BaseViewModel
import com.rzm.myjetpack.app.network.apiService
import com.rzm.myjetpack.data.model.event.ToastEvent
import com.rzm.myjetpack.eventViewModel

class RegisterViewModel : BaseViewModel() {

    var username = ObservableField<String>()
    var password = ObservableField<String>()
    var password2 = ObservableField<String>()
    var isShowPassword = ObservableField<Boolean>()
    var isShowPassword2 = ObservableField<Boolean>()
    private val _result = MutableLiveData<LoadingResult<Any>>()
    val result: LiveData<LoadingResult<Any>> = _result

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

    ///密码显示按钮是否显示
    var isPasswordVisible2 = object : ObservableInt(password2) {
        override fun get(): Int {
            return if (password2.get()?.isEmpty() == true) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    fun clear() {
        username.set("")
    }

    fun register() = when {
        (username.get() ?: "").isEmpty() -> {
            eventViewModel.toastEvent.value = ToastEvent.UsernameEmpty
        }
        (password.get() ?: "").isEmpty() -> {
            eventViewModel.toastEvent.value = ToastEvent.PasswordEmpty
        }
        else -> register(username.get()!!, password.get()!!, password2.get()!!)
    }

    var onShowPasswordCheckChangeListener =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            isShowPassword.set(isChecked)
        }

    var onShowPasswordCheckChangeListener2 =
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            isShowPassword2.set(isChecked)
        }

    private fun register(username: String, password: String, password2: String) {
        _result.value = LoadingResult.Loading
        launch({ apiService.register(username, password, password2) }, {
            if (it.isSuccess()) {
                _result.value = LoadingResult.Success(it)
            } else {
                _result.value = LoadingResult.Error(Exception(it.errorMsg))
            }
        }, {
            _result.value = LoadingResult.Error(it)
        })
    }
}