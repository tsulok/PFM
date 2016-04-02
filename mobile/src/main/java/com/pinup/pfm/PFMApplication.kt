package com.pinup.pfm

import android.app.Application
import com.pinup.pfm.ui.UIModule

/**
 * Main entry point of the application
 */
class PFMApplication : Application() {

    companion object {
        // platformStatic allow access it from java code
        @JvmStatic lateinit var injector: PFMApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        injector = DaggerPFMApplicationComponent.builder()
                .uIModule(UIModule(this))
                .build()
    }
}