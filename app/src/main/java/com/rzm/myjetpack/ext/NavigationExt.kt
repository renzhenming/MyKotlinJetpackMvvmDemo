package com.rzm.myjetpack.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.findNavController(): NavController {
    return NavHostFragment.findNavController(this)
}

var lastTimeMills: Long = 0L

fun NavController.beginNavigate(
    @IdRes resId: Int,
    @Nullable args: Bundle? = null,
    internal: Int = 500
) {
    val current = System.currentTimeMillis()
    if (current >= lastTimeMills + internal) {
        lastTimeMills = current
        navigate(resId, args)
    }
}