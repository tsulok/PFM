package com.pinup.pfm.di.module

import android.accounts.AccountManager
import android.app.Application
import android.content.Context
import com.pinup.pfm.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides


/**
 * DI modules for Application
 */
@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun provideAccountManager(): AccountManager {
        return AccountManager.get(application)
    }
}