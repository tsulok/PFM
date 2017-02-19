package com.pinup.pfm.di.component

import android.content.Context
import android.content.SharedPreferences
import com.pinup.pfm.di.module.*
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.model.database.DaoSession
import dagger.Component
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, InteractorModule::class, DaoModule::class,
        PresenterModule::class, UtilityModule::class) )
interface PFMApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun daoSession(): DaoSession
    fun pref(): SharedPreferences
    fun locationProvider(): ReactiveLocationProvider

    fun inject(activityModule: ActivityModule)
}
