package com.pinup.pfm.ui.main_navigator.adapter

import android.support.v4.app.FragmentManager
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.di.qualifiers.ChildFragmentManager
import com.pinup.pfm.ui.charts.ChartListFragment
import com.pinup.pfm.ui.core.adapter.BaseStatePagerAdapter
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.input.container.InputContainerFragment
import com.pinup.pfm.ui.settings.SettingsFragment
import javax.inject.Inject

/**
 * Pager adapter for main page navigator
 */
class MainNavigatorPagerAdapter @Inject constructor(@ChildFragmentManager fm: FragmentManager?)
    : BaseStatePagerAdapter<BaseFragment>(fm) {

    val settingsFragment: SettingsFragment by lazy { SettingsFragment() }
    val inputContainerFragment: InputContainerFragment by lazy { InputContainerFragment() }
    val chartListFragment: ChartListFragment by lazy { ChartListFragment() }

    private fun populateAdapter() {
        addItem(MainPageType.Chart.position, chartListFragment)
        addItem(MainPageType.Input.position, inputContainerFragment)
        addItem(MainPageType.Settings.position, settingsFragment)
    }

    enum class MainPageType constructor(val position: Int) {
        Chart(0),
        Input(1),
        Settings(2);
    }

    init {
        populateAdapter()
    }
}