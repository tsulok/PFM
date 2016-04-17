package com.pinup.pfm.ui

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.pinup.pfm.ui.category.CategoryListFragment
import com.pinup.pfm.ui.category.adapter.CategoryListAdapter
import com.pinup.pfm.ui.charts.ChartListFragment
import com.pinup.pfm.ui.history.HistoryListFragment
import com.pinup.pfm.ui.history.adapter.HistoryListAdapter
import com.pinup.pfm.ui.input.container.InputContainerFragment
import com.pinup.pfm.ui.input.main.InputMainFragment
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import com.pinup.pfm.ui.settings.SettingsFragment
import dagger.Module
import dagger.Provides

/**
 * DI modules for Activity
 */
@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun provideSupportFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    fun provideCategoryListFragment(): CategoryListFragment {
        return CategoryListFragment()
    }

    @Provides
    fun provideCategoryAdapter(): CategoryListAdapter {
        return CategoryListAdapter(activity)
    }

    @Provides
    fun provideChartListFragment(): ChartListFragment {
        return ChartListFragment()
    }

    @Provides
    fun provideInputMainFragment(): InputMainFragment {
        return InputMainFragment()
    }

    @Provides
    fun provideInputContainerFragment(): InputContainerFragment {
        return InputContainerFragment()
    }

    @Provides
    fun provideMainNavigatorFragment(): MainNavigatorFragment {
        return MainNavigatorFragment()
    }

    @Provides
    fun provideMainNavigatorAdapter(fragmentManager: FragmentManager): MainNavigatorPagerAdapter {
        return MainNavigatorPagerAdapter(fragmentManager)
    }

    @Provides
    fun provideTransactionHistoryListFragment(): HistoryListFragment {
        return HistoryListFragment()
    }

    @Provides
    fun provideTransactionHistoryAdapter(context: Context): HistoryListAdapter {
        return HistoryListAdapter(context)
    }

    @Provides
    fun provideSettingsFragment(): SettingsFragment {
        return SettingsFragment()
    }
}