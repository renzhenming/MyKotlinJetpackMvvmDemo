package com.rzm.myjetpack.ui.main

import android.os.Bundle
import com.rzm.myjetpack.R
import com.rzm.myjetpack.databinding.FragmentMainBinding
import com.rzm.myjetpack.app.base.BaseFragment
import com.rzm.myjetpack.appViewModel
import com.rzm.myjetpack.ext.init
import com.rzm.myjetpack.ext.initAdapter
import com.rzm.myjetpack.ext.setUiTheme
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentViewModel, FragmentMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.mainViewpager.initAdapter(this)
        binding.mainBottom.init {
            when (it) {
                R.id.menu_main -> mainViewpager.setCurrentItem(0, false)
                R.id.menu_project -> mainViewpager.setCurrentItem(1, false)
                R.id.menu_system -> mainViewpager.setCurrentItem(2, false)
                R.id.menu_public -> mainViewpager.setCurrentItem(3, false)
                R.id.menu_me -> mainViewpager.setCurrentItem(4, false)
            }
        }
    }

    override fun createObserver() {
        appViewModel.appColor.observe(this) {
            setUiTheme(it, mainBottom)
        }
    }
}