package com.rzm.myjetpack.ext

import androidx.appcompat.widget.Toolbar
import com.rzm.myjetpack.R
import com.rzm.myjetpack.appContext
import com.rzm.myjetpack.utils.SettingUtil


fun Toolbar.init(titleStr: String = ""): Toolbar {
    setBackgroundColor(SettingUtil.getColor(appContext))
    title = titleStr
    return this
}

fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.drawable.ic_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    setBackgroundColor(SettingUtil.getColor(appContext))
    title = titleStr.toHtml()
    setNavigationIcon(backImg)
    setNavigationOnClickListener {
        onBack.invoke(this)
    }
    return this
}
