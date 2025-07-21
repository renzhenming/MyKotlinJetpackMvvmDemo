package com.rzm.myjetpack.ui.main.login

import android.os.Bundle
import com.rzm.myjetpack.R
import com.rzm.myjetpack.app.base.BaseFragment
import com.rzm.myjetpack.appViewModel
import com.rzm.myjetpack.data.model.event.NavigationEvent
import com.rzm.myjetpack.data.model.event.ToastEvent
import com.rzm.myjetpack.databinding.FragmentLoginBinding
import com.rzm.myjetpack.eventViewModel
import com.rzm.myjetpack.ext.*
import com.rzm.myjetpack.ui.main.login.viewmodel.LoginViewModel
import com.rzm.myjetpack.utils.CacheUtil
import com.rzm.myjetpack.utils.ToastManager
import kotlinx.android.synthetic.main.include_toolbar.*

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.mViewModel = mViewModel
        toolbar.initClose(getString(R.string.login)) {
            findNavController().navigateUp()
        }
    }

    override fun createObserver() {
        mViewModel.loginResult.observe(this) { resultState ->
            activity?.parseResult(resultState, {
                CacheUtil.setUser(it)
                CacheUtil.setIsLogin(true)
                appViewModel.userInfo.value = it
                findNavController().navigateUp()
            }, {
                activity?.showAlertDialog(it.message ?: "error")
            })
        }
        mViewModel.navigationEvents.observe(this) {
            when (it) {
                is NavigationEvent.ToRegister -> {
                    hideSoftKeyboard(activity)
                    findNavController().navigate(R.id.action_loginFragment_to_registerFrgment)
                }
            }
        }
        eventViewModel.toastEvent.observe(this) {
            when (it) {
                is ToastEvent.UsernameEmpty -> {
                    ToastManager.showToast(getString(R.string.username_cannot_be_empty))
                }
                is ToastEvent.PasswordEmpty -> {
                    ToastManager.showToast(getString(R.string.password_cannot_be_empty))
                }
            }
        }
    }
}