package com.rzm.myjetpack.app.event

import androidx.lifecycle.MutableLiveData
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel
import com.rzm.myjetpack.appContext
import com.rzm.myjetpack.data.model.UserInfo
import com.rzm.myjetpack.utils.CacheUtil
import com.rzm.myjetpack.utils.SettingUtil

class AppViewModel : AbstractViewModel() {
    //App的账户信息
    val userInfo = MutableLiveData<UserInfo>()

    //App主题颜色
    var appColor = MutableLiveData<Int>()

    //App 列表动画
    var appAnimation = MutableLiveData<Int>()

    init {
        userInfo.value = CacheUtil.getUser()
        appColor.value = SettingUtil.getColor(appContext)
        appAnimation.value = SettingUtil.getListMode()
    }
}