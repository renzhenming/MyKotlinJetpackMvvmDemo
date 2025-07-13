package com.rzm.myjetpack.ui.main.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rzm.jetpackmvvm.base.ext.request
import com.rzm.myjetpack.app.base.BaseViewModel
import com.rzm.myjetpack.app.network.apiService
import com.rzm.myjetpack.data.model.ApiPagerResponse
import com.rzm.myjetpack.data.model.ApiResponse
import com.rzm.myjetpack.data.model.ArticleResponse
import com.rzm.myjetpack.data.model.ListDataUiState
import com.rzm.myjetpack.utils.CacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    //页码 首页数据页码从0开始
    var pageNo = 0

    //首页文章列表数据
    var homeDataState: MutableLiveData<ListDataUiState<ArticleResponse>> = MutableLiveData()

    /**
     * 获取首页文章列表数据
     * @param isRefresh 是否是刷新，即第一页
     */
    fun getHomeData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ getHomeData(pageNo) }, {
            pageNo++
            val listDataUiState = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            homeDataState.value = listDataUiState
        }, {
            val listDataUiState = ListDataUiState(
                isSuccess = false,
                errMessage = it.errorMsg,
                isRefresh = isRefresh,
                listData = arrayListOf<ArticleResponse>()
            )
            homeDataState.value = listDataUiState
        })
    }

    private suspend fun getHomeData(pageNo: Int): ApiResponse<ApiPagerResponse<ArrayList<ArticleResponse>>> {
        //同时异步请求2个接口，请求完成后合并数据
        return withContext(Dispatchers.IO) {
            val listData = async { apiService.getAritrilList(pageNo) }
            //如果App配置打开了首页请求置顶文章，且是第一页
            if (CacheUtil.isNeedTop() && pageNo == 0) {
                val topData = async { apiService.getTopAritrilList() }
                listData.await().data.datas.addAll(0, topData.await().data)
                listData.await()
            } else {
                listData.await()
            }
        }
    }
}