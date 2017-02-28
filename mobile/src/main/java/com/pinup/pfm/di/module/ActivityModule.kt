package com.pinup.pfm.di.module

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.pinup.pfm.di.qualifiers.ActivityContext
import com.pinup.pfm.di.qualifiers.SupportFragmentManager
import dagger.Module
import dagger.Provides

/**
 * DI modules for Activity
 */
@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @SupportFragmentManager
    @Provides
    fun provideFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }
}