package com.rzm.myjetpack.ui.main.web.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rzm.jetpackmvvm.base.ext.request
import com.rzm.jetpackmvvm.base.state.CollectState
import com.rzm.myjetpack.app.base.BaseViewModel
import com.rzm.myjetpack.app.network.apiService

class WebViewModel : BaseViewModel() {

    val collectState = MutableLiveData<CollectState>()

    fun collect(articleId: Int) {
        request({ apiService.collect(articleId) }, {
            collectState.value = CollectState(isSuccess = true, collected = true, id = articleId)
        }, {
            collectState.value = CollectState(
                isSuccess = false,
                collected = false,
                id = articleId,
                errorMsg = it.errorMsg
            )
        })
    }

    fun unCollect(articleId: Int) {
        request({ apiService.uncollect(articleId)},{
            collectState.value = CollectState(isSuccess = true, collected = false, id = articleId)
        },{
            collectState.value = CollectState(
                isSuccess = false,
                collected = true,
                id = articleId,
                errorMsg = it.errorMsg
            )
        })
    }

    //是否收藏
    var collected = false

    //收藏的Id
    var articleId = 0

    //标题
    var showTitle: String = ""

    //文章的网络访问路径
    var url: String = ""

    //需要收藏的类型 具体参数说明请看 CollectType 枚举类
    var collectType = 0
}