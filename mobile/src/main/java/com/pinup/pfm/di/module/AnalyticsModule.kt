package com.pinup.pfm.di.module

import android.content.Context
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.pinup.pfm.R
import com.pinup.pfm.di.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AnalyticsModule {

    @Provides
    @Singleton
    fun provideTracker(@ApplicationContext context: Context): Tracker {
        return GoogleAnalytics.getInstance(context).newTracker(R.xml.app_tracker)
    }
}