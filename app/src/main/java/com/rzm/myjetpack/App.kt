package com.rzm.myjetpack

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.rzm.myjetpack.app.event.AppViewModel
import com.rzm.myjetpack.app.event.EventViewModel
import com.rzm.myjetpack.ui.widget.callback.EmptyCallback
import com.rzm.myjetpack.ui.widget.callback.ErrorCallback
import com.rzm.myjetpack.ui.widget.callback.LoadingCallback
import com.tencent.mmkv.MMKV

//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val appViewModel: AppViewModel by lazy { App.appViewModelInstance }

//Application全局的ViewModel，用于发送全局通知操作
val eventViewModel: EventViewModel by lazy { App.appEventViewModelInstance }

val appContext: Context by lazy { App.instance }

class App : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        lateinit var instance: App
        lateinit var appViewModelInstance: AppViewModel
        lateinit var appEventViewModelInstance: EventViewModel
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        mAppViewModelStore = ViewModelStore()
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)
        appEventViewModelInstance = getAppViewModelProvider().get(EventViewModel::class.java)
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
    }

    private fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}