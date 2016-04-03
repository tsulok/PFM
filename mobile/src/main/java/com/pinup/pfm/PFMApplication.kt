package com.pinup.pfm

import android.app.Application
import com.pinup.pfm.interactor.DaoModule
import com.pinup.pfm.interactor.InteractorModule
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
                .interactorModule(InteractorModule())
                .daoModule(DaoModule(this))
                .build()
    }

    fun setCustomInjector(customInjector: PFMApplicationComponent) {
        injector = customInjector
    }
}