package com.rzm.myjetpack.ui.main.home.adater

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rzm.myjetpack.R
import com.rzm.myjetpack.ext.setAdapterAnimation
import com.rzm.myjetpack.ext.toHtml
import com.rzm.myjetpack.ui.main.home.bean.ArticleResponse
import com.rzm.myjetpack.ui.widget.customview.CollectView
import com.rzm.myjetpack.utils.SettingUtil

class ArticleAdapter(data: MutableList<ArticleResponse>?) :
    BaseDelegateMultiAdapter<ArticleResponse, BaseViewHolder>(data) {

    private val ITEM_TYPE_ARTICLE = 1//文章类型
    private val ITEM_TYPE_PROJECT = 2//项目类型

    private var showTag = false

    private var collectAction: (item: ArticleResponse, view: CollectView, position: Int) -> Unit =
        { _: ArticleResponse, _: CollectView, _: Int -> }

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
        when (holder.itemViewType) {
            ITEM_TYPE_ARTICLE -> {
                bindArticleViewHolder(holder, item)
            }
            ITEM_TYPE_PROJECT -> {
                bindProjectViewHolder(holder, item)
            }
        }
    }

    private fun bindProjectViewHolder(holder: BaseViewHolder, item: ArticleResponse) {
        item.run {
            holder.setText(R.id.item_project_author, author.ifEmpty { shareUser })
            holder.setText(R.id.item_project_title, title.toHtml())
            holder.setText(R.id.item_project_content, desc.toHtml())
            holder.setText(R.id.item_project_type, "$superChapterName·$chapterName".toHtml())
            holder.setText(R.id.item_project_date, niceDate)
            if (showTag) {
                holder.setGone(R.id.item_project_new, !fresh)
                holder.setGone(R.id.item_project_top, type != 1)
                if (tags.isNotEmpty()) {
                    holder.setGone(R.id.item_project_type1, false)
                    holder.setText(R.id.item_project_type1, tags[0].name)
                } else {
                    holder.setGone(R.id.item_project_type1, true)
                }
            } else {
                holder.setGone(R.id.item_project_top, true)
                holder.setGone(R.id.item_project_type1, true)
                holder.setGone(R.id.item_project_new, true)
            }
            holder.getView<CollectView>(R.id.item_project_collect).isChecked = collect
            Glide.with(context).load(envelopePic)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(holder.getView(R.id.item_project_imageview))
        }
        holder.getView<CollectView>(R.id.item_project_collect)
            .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                override fun onClick(v: CollectView) {
                    collectAction.invoke(item, v, holder.adapterPosition)
                }
            })
    }

    private fun bindArticleViewHolder(holder: BaseViewHolder, item: ArticleResponse) {
        item.run {
            holder.setText(R.id.item_home_author, author.ifEmpty { shareUser })
            holder.setText(R.id.item_home_content, title.toHtml())
            holder.setText(R.id.item_home_type2, "$superChapterName·$chapterName".toHtml())
            holder.setText(R.id.item_home_date, niceDate)
            holder.getView<CollectView>(R.id.item_home_collect).isChecked = collect
            if (showTag) {
                holder.setGone(R.id.item_home_new, !fresh)
                holder.setGone(R.id.item_home_top, type != 1)
                if (tags.isNotEmpty()) {
                    holder.setGone(R.id.item_home_type1, false)
                    holder.setText(R.id.item_home_type1, tags[0].name)
                } else {
                    holder.setGone(R.id.item_home_type1, true)
                }
            } else {
                holder.setGone(R.id.item_home_top, true)
                holder.setGone(R.id.item_home_type1, true)
                holder.setGone(R.id.item_home_new, true)
            }
            holder.getView<CollectView>(R.id.item_home_collect)
                .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                    override fun onClick(v: CollectView) {
                        collectAction.invoke(item, v, holder.adapterPosition)
                    }
                })
        }
    }
}
