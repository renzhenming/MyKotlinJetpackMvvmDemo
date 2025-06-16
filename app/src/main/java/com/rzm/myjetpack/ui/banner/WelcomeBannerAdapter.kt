package com.rzm.myjetpack.ui.banner

import android.view.View
import com.rzm.myjetpack.R
import com.zhpan.bannerview.BaseBannerAdapter

class WelcomeBannerAdapter : BaseBannerAdapter<String, WelcomeBannerHolder>() {
    override fun createViewHolder(itemView: View, viewType: Int): WelcomeBannerHolder {
        return WelcomeBannerHolder(itemView)
    }

    override fun onBind(holder: WelcomeBannerHolder?, data: String?, position: Int, pageSize: Int) {
        holder?.bindData(data, position, pageSize)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemwelcome
    }
}