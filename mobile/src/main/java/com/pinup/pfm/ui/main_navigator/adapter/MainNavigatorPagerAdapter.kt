package com.pinup.pfm.ui.main_navigator.adapter

import android.support.v4.app.FragmentManager
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.ui.core.adapter.BaseStatePagerAdapter
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.settings.ChartListFragment
import com.pinup.pfm.ui.settings.InputFragment
import com.pinup.pfm.ui.settings.SettingsFragment
import javax.inject.Inject

/**
 * Pager adapter for main page navigator
 */
class MainNavigatorPagerAdapter : BaseStatePagerAdapter<BaseFragment> {

    @Inject lateinit var settingsFragment: SettingsFragment
    @Inject lateinit var inputFragment: InputFragment
    @Inject lateinit var chartListFragment: ChartListFragment

    constructor(fm: FragmentManager?) : super(fm) {
        PFMApplication.activityInjector?.inject(this)
        populateAdapter()
    }

    private fun populateAdapter() {
        addItem(MainPageType.Chart.position, chartListFragment)
        addItem(MainPageType.Input.position, inputFragment)
        addItem(MainPageType.Settings.position, settingsFragment)
    }

    enum class MainPageType constructor(val position: Int) {
        Chart(0),
        Input(1),
        Settings(2);
    }
}