package com.rzm.jetpackmvvm.base.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rzm.jetpackmvvm.base.state.AppException
import com.rzm.jetpackmvvm.base.state.BaseResponse
import com.rzm.jetpackmvvm.base.state.ExceptionHandle
import com.rzm.jetpackmvvm.base.state.LoadingResult
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel
import kotlinx.coroutines.*

/**
 * net request 不校验请求结果数据是否是成功
 * @param block 请求体方法
 * @param result 请求回调的LoadingResult数据
 * @param isShowDialog 是否显示加载框
 */
fun <T> AbstractViewModel.request(
    block: suspend () -> BaseResponse<T>,
    result: MutableLiveData<LoadingResult<T>>,
    isShowDialog: Boolean = false,
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) {
                result.value = LoadingResult.Loading
            }
            block()
        }.onSuccess {
            if (it.isSuccess()) {
                result.value = LoadingResult.Success(it.getResponseData())
            } else {
                result.value = LoadingResult.Error(Exception(it.getResponseMsg()))
            }
        }.onFailure {
            it.message?.loge()
            result.value = LoadingResult.Error(Exception(it.message))
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> AbstractViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) loadingChange.showDialog.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    success(t)
                }
            }.onFailure { e ->
                //打印错误消息
                e.message?.loge()
                //打印错误栈信息
                e.printStackTrace()
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            loadingChange.dismissDialog.postValue(false)
            //打印错误消息
            it.message?.loge()
            //打印错误栈信息
            it.printStackTrace()
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when {
            response.isSuccess() -> {
                success(response.getResponseData())
            }
            else -> {
                throw AppException(
                    response.getResponseCode(),
                    response.getResponseMsg(),
                    response.getResponseMsg()
                )
            }
        }
    }
}

/**
 *  调用协程
 * @param block 操作耗时操作任务
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> AbstractViewModel.launch(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        kotlin.runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}
