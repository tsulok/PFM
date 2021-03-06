package com.pinup.pfm.di.module

import android.content.Context
import com.patloew.rxlocation.RxLocation
import com.pinup.pfm.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

/**
 * Module for utilities
 */
@Module
class UtilityModule {

    @Provides
    fun provideReactiveLocationProvider(@ApplicationContext context: Context): RxLocation {
        return RxLocation(context)
    }

    @Provides
    @Singleton
    fun provideEventBus(): EventBus {
        return EventBus.builder()
                .logNoSubscriberMessages(false)
                .build()
    }
}