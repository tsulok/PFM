package com.pinup.pfm

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.pinup.pfm.interactor.DaoModule
import com.pinup.pfm.interactor.InteractorModule
import com.pinup.pfm.ui.ActivityModule
import com.pinup.pfm.ui.UIModule

/**
 * Main entry point of the application
 */
class PFMApplication : Application() {

    companion object {
        // platformStatic allow access it from java code
        @JvmStatic lateinit var injector: PFMApplicationComponent
        @JvmStatic var activityInjector: PFMActivityComponent? = null

        @JvmStatic fun injectActivityInjector(activity: AppCompatActivity) {
            activityInjector = injector.inject(ActivityModule(activity))
//            DaggerPFMActivityComponent.builder()
//                    .activityModule(ActivityModule(activity))
//                    .build()
        }

        @JvmStatic fun resetActivityInjector() {
            activityInjector = null
        }
    }

    override fun onCreate() {
        super.onCreate()

        Logger.init("PFM")

        MultiDex.install(this)
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