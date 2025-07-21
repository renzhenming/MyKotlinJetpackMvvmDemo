package com.rzm.myjetpack.ui.main.login

import android.os.Bundle
import com.rzm.myjetpack.R
import com.rzm.myjetpack.app.base.BaseFragment
import com.rzm.myjetpack.data.model.event.ToastEvent
import com.rzm.myjetpack.databinding.FragmentRegisterBinding
import com.rzm.myjetpack.eventViewModel
import com.rzm.myjetpack.ext.*
import com.rzm.myjetpack.ui.main.login.viewmodel.RegisterViewModel
import com.rzm.myjetpack.utils.ToastManager
import kotlinx.android.synthetic.main.include_toolbar.*

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.viewmodel = mViewModel
        toolbar.initClose(getString(R.string.register)) {
            findNavController().navigateUp()
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.result.observe(this) {
            activity?.parseResult(it, {
                activity?.showShortToast(getString(R.string.register_success))
                findNavController().navigateUp()
            }, { e ->
                activity?.showAlertDialog(e.message ?: "error")
            })
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