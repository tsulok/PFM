package com.pinup.pfm.ui

import android.app.Activity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import com.pinup.pfm.ui.settings.ChartListFragment
import com.pinup.pfm.ui.settings.InputFragment
import com.pinup.pfm.ui.settings.SettingsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
    fun provideMainNavigatorFragment(): MainNavigatorFragment {
        return MainNavigatorFragment()
    }

    @Provides
    fun provideSettingsFragment(): SettingsFragment {
        return SettingsFragment()
    }

    @Provides
    fun provideInputFragment(): InputFragment {
        return InputFragment()
    }

    @Provides
    fun provideChartListFragment(): ChartListFragment {
        return ChartListFragment()
    }

    @Provides
    fun provideMainNavigatorAdapter(fragmentManager: FragmentManager): MainNavigatorPagerAdapter {
        return MainNavigatorPagerAdapter(fragmentManager)
    }
}