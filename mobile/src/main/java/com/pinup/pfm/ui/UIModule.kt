package com.pinup.pfm.ui

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

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
}