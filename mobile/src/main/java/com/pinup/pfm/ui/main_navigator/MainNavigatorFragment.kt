package com.pinup.pfm.ui.main_navigator

import android.support.v4.view.ViewPager
import android.view.View
import butterknife.Bind
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import javax.inject.Inject

/**
 * Main fragment for navigation
 */
class MainNavigatorFragment : BaseFragment, MainNavigatorScreen {

    @Inject lateinit var adapter: MainNavigatorPagerAdapter
    @Bind(R.id.viewpager) lateinit var viewPager: ViewPager

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_navigator
    }

    override fun initObjects(view: View?) {
        viewPager.adapter = adapter
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun navigateToSettings() {

    }

    override fun navigateToCharts() {

    }
}