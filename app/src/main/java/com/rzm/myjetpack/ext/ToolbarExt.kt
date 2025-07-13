package com.rzm.myjetpack.ext

import androidx.appcompat.widget.Toolbar
import com.rzm.myjetpack.appContext
import com.rzm.myjetpack.utils.SettingUtil


fun Toolbar.init(titleStr: String = ""): Toolbar {
    setBackgroundColor(SettingUtil.getColor(appContext))
    title = titleStr
    return this
}