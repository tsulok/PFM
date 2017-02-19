package com.pinup.pfm.di.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.pinup.pfm.di.qualifiers.ActivityContext
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.di.qualifiers.SupportFragmentManager
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

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @SupportFragmentManager
    @Provides
    fun provideFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    fun provideSupportFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }
}