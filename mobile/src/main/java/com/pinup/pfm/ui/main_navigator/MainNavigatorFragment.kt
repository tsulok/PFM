package com.pinup.pfm.ui.main_navigator

import android.support.v4.view.ViewPager
import android.view.View
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

/**
 * Main fragment for navigation
 */
class MainNavigatorFragment : BaseFragment(), MainNavigatorScreen {

    @Inject lateinit var adapter: MainNavigatorPagerAdapter
    @Inject lateinit var presenter: MainNavigatorPresenter

    val viewPager by lazy { find<ViewPager>(R.id.viewpager) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_navigator
    }

    override fun getPresenter(): IBasePresenter? = presenter
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initObjects(view: View?) {
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = adapter
        navigateToMain()
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

    fun navigateToMain() {
        viewPager.setCurrentItem(
                MainNavigatorPagerAdapter.MainPageType.Input.position, true)
    }
}