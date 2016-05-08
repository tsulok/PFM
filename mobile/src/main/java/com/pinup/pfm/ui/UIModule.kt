package com.pinup.pfm.ui

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
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