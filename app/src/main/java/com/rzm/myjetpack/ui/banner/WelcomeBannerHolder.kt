package com.rzm.myjetpack.ui.banner

import android.view.View
import android.widget.TextView
import com.rzm.myjetpack.R
import com.zhpan.bannerview.BaseViewHolder

class WelcomeBannerHolder(view: View) : BaseViewHolder<String>(view) {
    override fun bindData(data: String?, position: Int, pageSize: Int) {
        val textView = findView<TextView>(R.id.banner_text)
        textView.text = data
    }
}