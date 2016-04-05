package com.pinup.pfm.ui.main_navigator.adapter

import android.support.v4.app.FragmentManager
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

    constructor(fm: FragmentManager?) : super(fm)

    private fun populateAdapter() {
        addItem(chartListFragment)
        addItem(inputFragment)
        addItem(settingsFragment)
    }
}