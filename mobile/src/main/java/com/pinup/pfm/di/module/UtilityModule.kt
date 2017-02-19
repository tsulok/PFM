package com.pinup.pfm.di.module

import android.content.Context
import com.pinup.pfm.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation.ReactiveLocationProvider

/**
 * Module for utilities
 */
@Module
class UtilityModule {

    @Provides
    fun provideReactiveLocationProvider(@ApplicationContext context: Context): ReactiveLocationProvider {
        return ReactiveLocationProvider(context)
    }
}
