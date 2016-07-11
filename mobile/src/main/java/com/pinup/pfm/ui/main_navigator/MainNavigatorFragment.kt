package com.pinup.pfm.ui.main_navigator

import android.support.v4.view.ViewPager
import android.view.View
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import org.jetbrains.anko.support.v4.find

/**
 * Main fragment for navigation
 */
class MainNavigatorFragment : BaseFragment, MainNavigatorScreen {

//    @Inject lateinit var adapter: MainNavigatorPagerAdapter
    val viewPager by lazy { find<ViewPager>(R.id.viewpager) }

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_navigator
    }

    // TODO create presenter
    override fun getPresenter(): IBasePresenter? = null
    override fun getScreen(): BaseScreen = this

    override fun initObjects(view: View?) {
        // TODO how to inject childFragmentManager with dagger to a desired fragment
        viewPager.adapter = MainNavigatorPagerAdapter(childFragmentManager)
//        viewPager.adapter = adapter
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun navigateToSettings() {
        viewPager.setCurrentItem(
                MainNavigatorPagerAdapter.MainPageType.Settings.position, true)
    }

    override fun navigateToCharts() {
        viewPager.setCurrentItem(
                MainNavigatorPagerAdapter.MainPageType.Chart.position, true)
    }
}