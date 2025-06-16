package com.rzm.jetpackmvvm.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rzm.jetpackmvvm.base.ext.getVmClazz
import com.rzm.jetpackmvvm.base.ext.notNull
import com.rzm.jetpackmvvm.base.network.state.NetState
import com.rzm.jetpackmvvm.base.network.state.NetworkStateManager
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel

abstract class AbstractMvvmActivity<VM : AbstractViewModel> : AppCompatActivity() {

    lateinit var mViewMode: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding().notNull({
            setContentView(it)
        }, {
            setContentView(layoutId())
        })
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewMode = createViewModel()
        registerUiChange()
        initView(savedInstanceState)
        createObserver()
        initNetworkStateListener()
    }

    /**
     * 布局id
     */
    abstract fun layoutId(): Int

    /**
     * 初始化databinding
     */
    open fun initBinding(): View? {
        return null
    }

    /**
     * 隐藏加载弹窗
     */
    abstract fun dismissLoadingDialog()

    /**
     * 显示加载弹窗
     */
    abstract fun showLoadingDialog(it: String = "loading...")


    /**
     * 初始化页面网络状态监听
     */
    private fun initNetworkStateListener() {
        NetworkStateManager.instance.networkStateCallback.observe(this) {
            onNetworkStateChanged(it)
        }
    }

    /**
     * 需要根据网络状态变化而响应的页面重写此方法
     */
    open fun onNetworkStateChanged(it: NetState?) {
        //网络状态变化的监听
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 创建监听
     */
    abstract fun createObserver()

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    private fun registerUiChange() {
        mViewMode.loadingChange.showDialog.observe(this) {
            showLoadingDialog(it)
        }
        mViewMode.loadingChange.dismissDialog.observe(this) {
            dismissLoadingDialog()
        }
    }
}