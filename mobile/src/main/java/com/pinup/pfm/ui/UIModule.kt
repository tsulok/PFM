package com.pinup.pfm.ui

import android.content.Context
import android.content.res.Resources
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
class UIModule {

    val context: Context

    constructor(context: Context) {
        this.context = context
    }

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideResources(): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun provideSettingsFragment(): SettingsFragment {
        return SettingsFragment()
    }

    @Provides
    @Singleton
    fun provideInputFragment(): InputFragment {
        return InputFragment()
    }

    @Provides
    @Singleton
    fun provideChartListFragment(): ChartListFragment {
        return ChartListFragment()
    }
}