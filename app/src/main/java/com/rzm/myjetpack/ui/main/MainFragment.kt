package com.rzm.myjetpack.ui.main

import android.os.Bundle
import com.rzm.myjetpack.R
import com.rzm.myjetpack.app.base.BaseFragment
import com.rzm.myjetpack.databinding.FragmentMainBinding
import com.rzm.myjetpack.ext.init
import com.rzm.myjetpack.ext.initAdapter
import com.rzm.myjetpack.ui.main.home.HomeFragment
import com.rzm.myjetpack.ui.main.me.MeFragment
import com.rzm.myjetpack.ui.main.project.ProjectFragment
import com.rzm.myjetpack.ui.main.publicnum.PublicNumberFragment
import com.rzm.myjetpack.ui.main.tree.TreeArrFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainFragmentViewModel, FragmentMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.mainViewpager.initAdapter(this, 5, fragmentCallback = { position ->
            when (position) {
                0 -> HomeFragment()
                1 -> ProjectFragment()
                2 -> TreeArrFragment()
                3 -> PublicNumberFragment()
                4 -> MeFragment()
                else -> HomeFragment()
            }
        }, offscreenPageLimit = 5)
        binding.mainBottom.init {
            when (it) {
                R.id.menu_main -> {
                    mainViewpager.setCurrentItem(0, false)
                }
                R.id.menu_project -> {
                    mainViewpager.setCurrentItem(1, false)
                }
                R.id.menu_system -> {
                    mainViewpager.setCurrentItem(2, false)
                }
                R.id.menu_public -> {
                    mainViewpager.setCurrentItem(3, false)
                }
                R.id.menu_me -> {
                    mainViewpager.setCurrentItem(4, false)
                }
            }
        }
    }
}