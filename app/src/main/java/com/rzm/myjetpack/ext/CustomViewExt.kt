package com.rzm.myjetpack.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rzm.myjetpack.appContext
import com.rzm.myjetpack.utils.SettingUtil


/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

fun initLoadState(view: View, callback: () -> Unit): LoadService<Any> {
    val loadState = LoadSir.getDefault().register(view) {
        callback.invoke()
    }
    loadState.showSuccess()
    SettingUtil.setLoadingColor(SettingUtil.getColor(appContext), loadState)
    return loadState
}


//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}


