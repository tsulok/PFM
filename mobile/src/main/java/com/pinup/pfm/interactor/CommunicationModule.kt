package com.pinup.pfm.interactor

import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

/**
 * Module for communications
 */
@Module
class CommunicationModule {

    @Singleton
    @Provides
    fun provideEventbus(): EventBus {
        return EventBus.getDefault();
    }
}