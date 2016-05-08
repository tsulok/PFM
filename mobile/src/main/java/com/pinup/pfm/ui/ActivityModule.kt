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
import com.pinup.pfm.ui.input.action.InputActionContainerFragment
import com.pinup.pfm.ui.input.action.camera.InputActionCameraFragment
import com.pinup.pfm.ui.input.action.date.InputActionDateFragment
import com.pinup.pfm.ui.input.action.description.InputActionDescriptionFragment
import com.pinup.pfm.ui.input.action.location.InputActionLocationFragment
import com.pinup.pfm.ui.input.container.InputContainerFragment
import com.pinup.pfm.ui.input.main.InputMainFragment
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import com.pinup.pfm.ui.settings.SettingsFragment
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
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

    @Singleton
    @Provides
    fun provideReactiveLocationProvider(context: Context): ReactiveLocationProvider {
        return ReactiveLocationProvider(context)
    }

    @Provides
    fun provideSupportFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    @Singleton
    fun provideCategoryListFragment(): CategoryListFragment {
        return CategoryListFragment()
    }

    @Provides
    fun provideCategoryAdapter(): CategoryListAdapter {
        return CategoryListAdapter(activity)
    }

    @Provides
    @Singleton
    fun provideChartListFragment(): ChartListFragment {
        return ChartListFragment()
    }

    @Provides
    @Singleton
    fun provideInputContainerFragment(): InputContainerFragment {
        return InputContainerFragment()
    }

    @Provides
    @Singleton
    fun provideInputMainFragment(): InputMainFragment {
        return InputMainFragment()
    }

    @Provides
    @Singleton
    fun provideInputActionContainerFragment(): InputActionContainerFragment {
        return InputActionContainerFragment()
    }

    @Provides
    @Singleton
    fun provideInputActionCameraFragment(): InputActionCameraFragment {
        return InputActionCameraFragment()
    }

    @Provides
    @Singleton
    fun provideInputActionDescriptionFragment(): InputActionDescriptionFragment {
        return InputActionDescriptionFragment()
    }

    @Provides
    @Singleton
    fun provideInputActionDateFragment(): InputActionDateFragment {
        return InputActionDateFragment()
    }

    @Provides
    @Singleton
    fun provideInputActionLocationFragment(): InputActionLocationFragment {
        return InputActionLocationFragment()
    }

    @Provides
    @Singleton
    fun provideMainNavigatorFragment(): MainNavigatorFragment {
        return MainNavigatorFragment()
    }

    @Provides
    @Singleton
    fun provideTransactionHistoryListFragment(): HistoryListFragment {
        return HistoryListFragment()
    }

    @Provides
    @Singleton
    fun provideTransactionHistoryAdapter(context: Context): HistoryListAdapter {
        return HistoryListAdapter(context)
    }

    @Provides
    @Singleton
    fun provideSettingsFragment(): SettingsFragment {
        return SettingsFragment()
    }
}