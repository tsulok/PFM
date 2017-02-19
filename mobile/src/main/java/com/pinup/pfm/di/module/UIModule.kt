package com.pinup.pfm.di.module

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

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