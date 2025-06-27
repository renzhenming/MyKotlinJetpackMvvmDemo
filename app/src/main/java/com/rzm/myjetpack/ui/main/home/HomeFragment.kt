package com.rzm.myjetpack.ui.main.home

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.rzm.myjetpack.R
import com.rzm.myjetpack.app.base.BaseFragment
import com.rzm.myjetpack.databinding.FragmentHomeBinding
import com.rzm.myjetpack.ext.*
import com.rzm.myjetpack.ui.main.home.adater.ArticleAdapter
import com.rzm.myjetpack.ui.main.home.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private lateinit var loadState: LoadService<Any>
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf(), true) }

    override fun initView(savedInstanceState: Bundle?) {
        //初始化状态页面
        loadState = initLoadState(binding.includeList.includeRecyclerview.swipeRefresh) {
            loadState.showLoading()
        }
        //初始化title bar
        binding.includeToolbar.toolbar.run {
            init("首页")
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> findNavController()
                        .beginNavigate(R.id.action_mainfragment_to_searchFragment)
                }
                true
            }
        }
        //初始化recyclerView
        binding.includeList.includeRecyclerview.recyclerView.init(
            LinearLayoutManager(context),
            articleAdapter
        ).let {

        }
    }
}