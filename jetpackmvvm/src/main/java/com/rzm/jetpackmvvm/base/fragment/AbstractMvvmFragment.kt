package com.rzm.jetpackmvvm.base.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.rzm.jetpackmvvm.base.ext.getVmClazz
import com.rzm.jetpackmvvm.base.network.state.NetState
import com.rzm.jetpackmvvm.base.network.state.NetworkStateManager
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel

abstract class AbstractMvvmFragment<VM : AbstractViewModel> : Fragment() {

    private lateinit var mActivity: AppCompatActivity
    private lateinit var mViewModel: VM
    private val mHandler = Handler(Looper.getMainLooper())
    private var isFirst: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    abstract fun layoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        registerUiChange()
        initData()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    /**
     * 隐藏加载弹窗
     */
    abstract fun dismissLoadingDialog()

    /**
     * 显示加载弹窗
     */
    abstract fun showLoadingDialog(it: String = "loading...")

    private fun registerUiChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner) {
            showLoadingDialog(it)
        }
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner) {
            dismissLoadingDialog()
        }
    }

    open fun onNetworkStateChanged(netState: NetState) {}

    open fun lazyLoadTime(): Long {
        return 300
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            mHandler.postDelayed({
                lazyLoadData()
                NetworkStateManager.instance.networkStateCallback.observe(viewLifecycleOwner) {
                    if (!isFirst) {
                        onNetworkStateChanged(it)
                    }
                }
                isFirst = false
            }, lazyLoadTime())
        }
    }
}