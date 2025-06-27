package com.rzm.myjetpack.ui.widget.callback

import com.kingja.loadsir.callback.Callback
import com.rzm.myjetpack.R

class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}