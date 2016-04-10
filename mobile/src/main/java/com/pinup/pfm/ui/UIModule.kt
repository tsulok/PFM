package com.pinup.pfm.ui

import android.content.Context
import android.content.res.Resources
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.pinup.pfm.ui.main_navigator.adapter.MainNavigatorPagerAdapter
import com.pinup.pfm.ui.settings.ChartListFragment
import com.pinup.pfm.ui.settings.InputFragment
import com.pinup.pfm.ui.settings.SettingsFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI modules for UI
 */
@Module
class UIModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideResources(): Resources {
        return context.resources
    }
}