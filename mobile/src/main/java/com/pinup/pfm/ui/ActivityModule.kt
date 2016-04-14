package com.pinup.pfm.ui

import android.app.Activity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.pinup.pfm.ui.input.category.CategoryListFragment
import com.pinup.pfm.ui.input.category.adapter.CategoryListAdapter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import com.pinup.pfm.ui.settings.ChartListFragment
import com.pinup.pfm.ui.settings.InputFragment
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
    fun provideInputFragment(): InputFragment {
        return InputFragment()
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
    fun provideSettingsFragment(): SettingsFragment {
        return SettingsFragment()
    }
}