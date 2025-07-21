package com.rzm.myjetpack.ext

import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rzm.jetpackmvvm.base.state.LoadingResult
import com.rzm.myjetpack.R
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

fun ComponentActivity.showAlertDialog(
    message: String,
    title: String = getString(R.string.tips),
    positiveButtonText: String = getString(R.string.confirm),
    positiveAction: () -> Unit = {},
    negativeButtonText: String = getString(R.string.cancel),
    negativeAction: () -> Unit = {}
) {

    AlertDialog.Builder(this)
        .setMessage(message)
        .setTitle(title)
        .setCancelable(true)
        .setPositiveButton(positiveButtonText) { _, _ ->
            positiveAction.invoke()
        }
        .setNegativeButton(negativeButtonText) { _, _ ->
            negativeAction.invoke()
        }
        .show()
}

fun ComponentActivity.showShortToast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun ComponentActivity.showLongToast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}

fun <T> ComponentActivity.parseResult(
    result: LoadingResult<T>,
    onSuccess: (T) -> Unit,
    onError: ((Throwable) -> Unit) = {},
    onLoading: (() -> Unit) = {},
) {
    when (result) {
        is LoadingResult.Loading -> {
            showProgress()
            onLoading.invoke()
        }
        is LoadingResult.Success -> {
            dismissProgress()
            onSuccess(result.data)
        }
        is LoadingResult.Error -> {
            dismissProgress()
            onError.invoke(result.exception)
        }
    }
}

private var ComponentActivity.currentProgressDialog: BottomSheetDialog? by WeakReferenceDelegate()

// 显示加载对话框
fun ComponentActivity.showProgress(
    message: String = getString(R.string.loading),
    isCancelable: Boolean = false,
    theme: Int = R.style.CustomBottomSheetDialogTheme
) {
    if (currentProgressDialog?.isShowing == true) return
    currentProgressDialog = BottomSheetDialog(this, theme).apply {
        setContentView(R.layout.dialog_progress_view)
        findViewById<TextView>(R.id.progress_message)?.text = message
        setCancelable(isCancelable)
        setCanceledOnTouchOutside(isCancelable)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        show()
    }
}

fun ComponentActivity.dismissProgress() {
    currentProgressDialog?.dismiss()
    currentProgressDialog = null
}

// 弱引用委托，避免内存泄漏
private class WeakReferenceDelegate<T : Any?> {
    private var weakReference: WeakReference<T>? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return weakReference?.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        weakReference = if (value != null) WeakReference(value) else null
    }
}
