package com.pinup.pfm

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDex
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.pinup.pfm.di.component.DaggerPFMApplicationComponent
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.di.component.PFMApplicationComponent
import com.pinup.pfm.di.module.DaoModule
import com.pinup.pfm.di.module.InteractorModule
import com.pinup.pfm.di.module.ActivityModule
import com.pinup.pfm.di.module.UIModule

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