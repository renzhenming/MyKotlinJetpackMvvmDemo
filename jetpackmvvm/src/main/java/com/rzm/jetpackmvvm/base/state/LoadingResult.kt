package com.rzm.jetpackmvvm.base.state

sealed class LoadingResult<out T> {
    data class Success<out T>(val data: T) : LoadingResult<T>()
    data class Error(val exception: Throwable) : LoadingResult<Nothing>()
    object Loading : LoadingResult<Nothing>()
}