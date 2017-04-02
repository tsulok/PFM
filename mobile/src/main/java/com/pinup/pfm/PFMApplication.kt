package com.pinup.pfm

import android.app.Application
import android.support.multidex.MultiDex
import com.facebook.FacebookSdk
import com.orhanobut.logger.Logger
import com.pinup.pfm.di.component.DaggerPFMApplicationComponent
import com.pinup.pfm.di.component.PFMApplicationComponent
import com.pinup.pfm.di.module.ApplicationModule

/**
 * Main entry point of the application
 */
class PFMApplication : Application() {

    companion object {
        // platformStatic allow access it from java code
        @JvmStatic lateinit var applicationComponent: PFMApplicationComponent
        @JvmStatic lateinit var application: PFMApplication
    }

    override fun onCreate() {
        super.onCreate()

        Logger.init("PFM")
        application = this

        MultiDex.install(this)
        applicationComponent = DaggerPFMApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        // Config facebook
        FacebookSdk.sdkInitialize(this)
    }

    fun setCustomInjector(customInjector: PFMApplicationComponent) {
        applicationComponent = customInjector
    }
}