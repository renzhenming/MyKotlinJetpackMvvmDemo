package com.rzm.myjetpack.ui

import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.rzm.jetpackmvvm.base.viewmodel.AbstractViewModel
import com.rzm.myjetpack.R
import com.rzm.myjetpack.app.base.BaseActivity
import com.rzm.myjetpack.databinding.ActivitySplashBinding
import com.rzm.myjetpack.ext.gone
import com.rzm.myjetpack.ext.visible
import com.rzm.myjetpack.ui.banner.WelcomeBannerAdapter
import com.rzm.myjetpack.ui.banner.WelcomeBannerHolder
import com.rzm.myjetpack.utils.CacheUtil
import com.rzm.myjetpack.utils.SettingUtil
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<AbstractViewModel, ActivitySplashBinding>() {

    private var list = arrayListOf("1", "2", "3")
    private lateinit var viewPager: BannerViewPager<String, WelcomeBannerHolder>

    inner class ProxyClick {
        fun toMain() {
            CacheUtil.setFirst(false)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.click = ProxyClick()
        mDatabind.welcomeBaseview.setBackgroundColor(SettingUtil.getColor(this))
        viewPager = findViewById(R.id.banner_view)
        if (CacheUtil.isFirst()) {
            welcome_image.gone()
            viewPager.apply {
                adapter = WelcomeBannerAdapter()
                setLifecycleRegistry(lifecycle)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        if (position == list.size - 1) {
                            welcomeJoin.visible()
                        } else {
                            welcomeJoin.gone()
                        }
                    }
                })
                create(list.toList())
            }
        } else {
            welcome_image.visible()
            viewPager.postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }, 2000);
        }
    }
}