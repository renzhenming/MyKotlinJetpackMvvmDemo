package com.rzm.myjetpack.app.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.rzm.jetpackmvvm.base.fragment.AbstractMvvmDbFragment
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel
import com.rzm.myjetpack.ext.hideSoftKeyboard

abstract class BaseFragment<VM : AbstractViewModel, DB : ViewDataBinding> :
    AbstractMvvmDbFragment<VM, DB>() {
    abstract override fun initView(savedInstanceState: Bundle?);

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    override fun lazyLoadTime(): Long {
        return 300
    }

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    override fun createObserver() {}

    /**
     * Fragment执行onViewCreated后触发
     */
    override fun initData() {

    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(activity)
    }

    override fun showLoadingDialog(it: String) {
    }

    override fun dismissLoadingDialog() {
    }
}