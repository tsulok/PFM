package com.pinup.pfm

import android.content.Context
import android.content.SharedPreferences
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.model.database.DaoMaster
import com.pinup.pfm.model.database.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI module for testing
 */
@Module
class TestModule(@ApplicationContext private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideDAOSession(): DaoSession {
        val openHelper = DaoMaster.DevOpenHelper(context, null, null)
        val daoMaster = DaoMaster(openHelper.writableDatabase)
        return daoMaster.newSession()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("PFMSharedPref_Test", Context.MODE_PRIVATE)
    }
}