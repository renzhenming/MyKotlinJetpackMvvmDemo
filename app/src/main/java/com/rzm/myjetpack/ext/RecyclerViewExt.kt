package com.rzm.myjetpack.ext

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kingja.loadsir.core.LoadService
import com.rzm.myjetpack.appContext
import com.rzm.myjetpack.data.model.ListDataUiState
import com.rzm.myjetpack.ui.widget.recyclerview.MyLoadMoreView
import com.rzm.myjetpack.utils.SettingUtil
import com.yanzhenjie.recyclerview.SwipeRecyclerView


fun SwipeRecyclerView.init(
    manager: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = manager
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRecyclerView.initFooter(loadMoreListener: SwipeRecyclerView.LoadMoreListener): MyLoadMoreView {
    val footerView = MyLoadMoreView(appContext)
    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
    footerView.setLoadMoreListener {
        footerView.onLoading()
        loadMoreListener.onLoadMore()
    }
    this.run {
        addFooterView(footerView)
        setLoadMoreView(footerView)
        setLoadMoreListener(loadMoreListener)
    }
    return footerView
}

fun RecyclerView.initFloatBtn(floatBtn: FloatingActionButton) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatBtn.visibility = View.INVISIBLE
            }
        }
    })
    floatBtn.backgroundTintList = SettingUtil.getOneColorStateList(appContext)
    floatBtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)
        } else {
            smoothScrollToPosition(0)
        }
    }
}

fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        setColorSchemeColors(SettingUtil.getColor(appContext))
    }
}


/**
 * 加载列表数据
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<*>,
    recyclerView: SwipeRecyclerView,
    swipeRefreshLayout: SwipeRefreshLayout
) {
    swipeRefreshLayout.isRefreshing = false
    recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
    if (data.isSuccess) {
        when {
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                loadService.showSuccess()
            }
            else -> {
                baseQuickAdapter.addData(data.listData)
                loadService.showSuccess()
            }
        }
    } else {
        if (data.isRefresh) {
            loadService.showError(data.errMessage)
        } else {
            recyclerView.loadMoreError(0, data.errMessage)
        }
    }
}