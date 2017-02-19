package com.pinup.pfm.di.module

import android.content.Context
import android.content.SharedPreferences
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.model.database.DaoMaster
import com.pinup.pfm.model.database.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI modules for Dao
 */
@Module
class DaoModule {

    @Singleton
    @Provides
    fun provideDAOSession(@ApplicationContext context: Context): DaoSession {
        val openHelper = DaoMaster.DevOpenHelper(context, "pfm-db", null)
        val daoMaster = DaoMaster(openHelper.writableDatabase)
        return daoMaster.newSession()
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PFMSharedPref", Context.MODE_PRIVATE)
    }
}