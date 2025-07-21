package com.rzm.myjetpack.ui.main.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Window
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import com.just.agentweb.AgentWeb
import com.rzm.myjetpack.R
import com.rzm.myjetpack.app.base.BaseFragment
import com.rzm.myjetpack.data.model.ArticleResponse
import com.rzm.myjetpack.data.model.enums.CollectType
import com.rzm.myjetpack.data.model.event.CollectEvent
import com.rzm.myjetpack.databinding.FragmentWebBinding
import com.rzm.myjetpack.eventViewModel
import com.rzm.myjetpack.ext.findNavController
import com.rzm.myjetpack.ext.hideSoftKeyboard
import com.rzm.myjetpack.ext.initClose
import com.rzm.myjetpack.ext.showAlertDialog
import com.rzm.myjetpack.ui.main.web.viewmodel.WebViewModel
import com.rzm.myjetpack.utils.CacheUtil
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.include_toolbar.view.*

class WebFragment : BaseFragment<WebViewModel, FragmentWebBinding>() {

    private lateinit var webView: AgentWeb.PreAgentWeb
    private var agentWeb: AgentWeb? = null

    companion object {
        const val PARAMS_URL = "web_url"
    }

    override fun initView(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        arguments?.run {
            getParcelable<ArticleResponse>(PARAMS_URL)?.let {
                mViewModel.articleId = it.id
                mViewModel.showTitle = it.title
                mViewModel.collected = it.collect
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Article.type
            }
        }
        binding.root.toolbar.run {
            mActivity.setSupportActionBar(this)
            initClose(mViewModel.showTitle) {
                hideSoftKeyboard(activity)
                onBackPressedClick()
            }
        }
        webView = AgentWeb.with(this)
            .setAgentWebParent(webContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.collectState.observe(this){
            if (it.isSuccess) {
                mViewModel.collected = it.collected
                eventViewModel.collectEvent.value = CollectEvent(it.id, it.collected)
                //刷新一下menu
                mActivity.window?.invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL)
                mActivity.invalidateOptionsMenu()
            } else {
                activity?.showAlertDialog(it.errorMsg)
            }
        }
    }

    override fun lazyLoadData() {
        agentWeb = webView.go(mViewModel.url)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressedClick()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.web_collect -> {
                collectWeb()
            }
            R.id.web_share -> {
                shareWeb()
            }
            R.id.web_refresh -> {
                refreshWeb()
            }
            R.id.web_origin -> {
                openWeb()
            }
        }
        return true
    }

    private fun collectWeb() {
        if (!CacheUtil.isLogin()) {
            findNavController().navigate(R.id.action_to_loginFragment)
            return
        }
        if (mViewModel.collected) {
            mViewModel.unCollect(mViewModel.articleId)
        } else {
            mViewModel.collect(mViewModel.articleId)
        }
    }

    private fun openWeb() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mViewModel.url)))
    }

    private fun refreshWeb() {
        agentWeb?.urlLoader?.reload()
    }

    private fun shareWeb() {
        startActivity(Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "{${mViewModel.showTitle}}:${mViewModel.url}")
            type = "text/plain"
        }, "分享到"))
    }

    private fun onBackPressedClick() {
        agentWeb?.let { web ->
            if (web.webCreator.webView.canGoBack()) {
                web.webCreator.webView.goBack()
            } else {
                findNavController().navigateUp()
            }
        }
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        mActivity.setSupportActionBar(null)
        super.onDestroy()
    }
}