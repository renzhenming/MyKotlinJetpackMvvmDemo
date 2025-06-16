package com.rzm.myjetpack.ext

import android.app.Activity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.rzm.myjetpack.R

private var dialog: MaterialDialog? = null

fun AppCompatActivity.showDialog(message: String = "loading") {
    if (!isFinishing) {
        if (dialog == null) {
            dialog = MaterialDialog(this)
                .cancelable(false)
                .cancelOnTouchOutside(true)
                .cornerRadius(10f)
                .customView(R.layout.layout_custom_progress_dialog_view)
                .lifecycleOwner(this)
            dialog?.getCustomView()?.run {
                findViewById<TextView>(R.id.loading_tips).text = message
            }
        }
        dialog!!.show()
    }
}

fun Activity.dismissDialog() {
    dialog?.dismiss()
    dialog = null
}