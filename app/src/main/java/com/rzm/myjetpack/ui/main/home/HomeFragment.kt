package com.rzm.myjetpack.ui.main.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.rzm.myjetpack.R
import com.rzm.myjetpack.app.base.BaseFragment
import com.rzm.myjetpack.databinding.FragmentHomeBinding
import com.rzm.myjetpack.ext.*
import com.rzm.myjetpack.ui.main.home.adater.ArticleAdapter
import com.rzm.myjetpack.ui.main.home.viewmodel.HomeViewModel
import com.rzm.myjetpack.ui.widget.recyclerview.MyLoadMoreView
import com.rzm.myjetpack.ui.widget.recyclerview.SpaceItemDecoration
import com.yanzhenjie.recyclerview.SwipeRecyclerView

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private lateinit var loadState: LoadService<Any>
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf(), true) }
    private lateinit var footView: MyLoadMoreView
    private val requestHomeViewModel: HomeViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        loadState = initLoadState(binding.includeList.includeRecyclerview.swipeRefresh) {
            loadState.showLoading()
        }
        binding.includeToolbar.toolbar.run {
            init("首页")
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                onMenuItemClick(it)
            }
        }
        binding.includeList.includeRecyclerview.recyclerView.init(
            LinearLayoutManager(context),
            articleAdapter
        ).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
            footView = it.initFooter(SwipeRecyclerView.LoadMoreListener {
                requestHomeViewModel.getHomeData(false)
            })
            it.initFloatBtn(binding.includeList.floatbtn)
        }
        binding.includeList.includeRecyclerview.swipeRefresh.init {
            requestHomeViewModel.getHomeData(true)
        }
        initAdapter()
    }

    private fun initAdapter() {
        articleAdapter.run {
            setOnItemClickListener { _, _, _ ->

            }
            addChildClickViewIds(R.id.item_home_author, R.id.item_project_author)
            setOnItemChildClickListener { _, view, position ->
                onListItemChildViewClick(view, position)
            }
        }
    }

    override fun lazyLoadData() {
        loadState.showLoading()
        requestHomeViewModel.getHomeData(true)
    }

    override fun createObserver() {
        requestHomeViewModel.run {
            homeDataState.observe(viewLifecycleOwner) {
                loadListData(
                    it, articleAdapter, loadState,
                    binding.includeList.includeRecyclerview.recyclerView,
                    binding.includeList.includeRecyclerview.swipeRefresh
                )
            }
        }
    }

    private fun onListItemChildViewClick(view: View, position: Int) {
        when (view.id) {
            R.id.item_home_author, R.id.item_project_author -> {
                findNavController().navigate(
                    R.id.action_mainfragment_to_lookInfoFragment,
                    Bundle()
                )
            }
        }
    }

    private fun onMenuItemClick(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.home_search -> findNavController()
                .beginNavigate(R.id.action_mainfragment_to_searchFragment)
        }
        return true
    }
}