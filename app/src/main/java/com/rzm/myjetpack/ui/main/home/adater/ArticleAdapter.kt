package com.rzm.myjetpack.ui.main.home.adater

import android.text.TextUtils
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rzm.myjetpack.R
import com.rzm.myjetpack.ext.setAdapterAnimation
import com.rzm.myjetpack.ui.main.home.bean.ArticleResponse
import com.rzm.myjetpack.utils.SettingUtil

class ArticleAdapter(data: MutableList<ArticleResponse>?) :
    BaseDelegateMultiAdapter<ArticleResponse, BaseViewHolder>(data) {

    private val ITEM_TYPE_ARTICLE = 1//文章类型
    private val ITEM_TYPE_PROJECT = 2//项目类型

    private var showTag = false

    constructor(data: MutableList<ArticleResponse>?, showTag: Boolean) : this(data) {
        this.showTag = showTag
    }

    init {
        setAdapterAnimation(SettingUtil.getListMode())
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<ArticleResponse>() {
            override fun getItemType(data: List<ArticleResponse>, position: Int): Int {
                return if (TextUtils.isEmpty(data[position].envelopePic)) ITEM_TYPE_ARTICLE else ITEM_TYPE_PROJECT
            }
        })
        getMultiTypeDelegate()?.run {
            addItemType(ITEM_TYPE_ARTICLE, R.layout.item_ariticle)
            addItemType(ITEM_TYPE_PROJECT, R.layout.item_project)
        }
    }

    override fun convert(
        holder: BaseViewHolder,
        item: ArticleResponse
    ) {

    }
}
