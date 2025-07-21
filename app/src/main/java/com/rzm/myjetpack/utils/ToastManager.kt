package com.rzm.myjetpack.utils

import android.app.Application
import android.widget.Toast
import com.rzm.myjetpack.appContext

object ToastManager {
    private var application: Application? = null
    private var currentToast: Toast? = null
    private var lastMessage: String? = null
    private var lastShowTime: Long = 0

    // 初始化，在 Application 中调用
    fun init(application: Application) {
        this.application = application
    }

    // 显示 Toast
    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        if (!isValidMessage(message)) return
        // 取消当前显示的 Toast
        currentToast?.cancel()
        // 创建新 Toast 并应用全局样式
        currentToast = Toast.makeText(appContext, message, duration).apply {
            show()
        }
        // 记录最后显示信息
        lastMessage = message
        lastShowTime = System.currentTimeMillis()
    }

    // 验证消息有效性（避免重复显示）
    private fun isValidMessage(message: String): Boolean {
        val currentTime = System.currentTimeMillis()
        // 2秒内相同内容不重复显示
        if (message == lastMessage && currentTime - lastShowTime < 2000) {
            return false
        }
        return true
    }
}
