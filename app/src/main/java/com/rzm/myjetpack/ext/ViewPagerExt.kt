package com.rzm.myjetpack.ext

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.initAdapter(
    fragment: Fragment,
    itemCount: Int,
    fragmentCallback: (Int) -> Fragment,
    isUserInputEnabled: Boolean = false,
    offscreenPageLimit: Int = 1
): ViewPager2 {
    this.isUserInputEnabled = isUserInputEnabled
    this.offscreenPageLimit = offscreenPageLimit
    this.adapter = object : FragmentStateAdapter(fragment) {
        override fun getItemCount() = itemCount
        override fun createFragment(position: Int): Fragment {
            return fragmentCallback.invoke(position)
        }
    }
    return this
}