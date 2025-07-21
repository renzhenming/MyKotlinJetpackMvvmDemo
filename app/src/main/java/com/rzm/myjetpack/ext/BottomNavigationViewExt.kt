package com.rzm.myjetpack.ext

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rzm.myjetpack.appContext
import com.rzm.myjetpack.utils.SettingUtil

fun BottomNavigationView.init(navigationItemSelectedAction: (Int) -> Unit): BottomNavigationView {
    itemIconTintList = SettingUtil.getColorStateList(SettingUtil.getColor(appContext))
    itemTextColor = SettingUtil.getColorStateList(appContext)
    setOnItemSelectedListener {
        navigationItemSelectedAction.invoke(it.itemId)
        true
    }
    return this
}

