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
open class DaoModule {

    @Singleton
    @Provides
    open fun provideOpenHelper(@ApplicationContext context: Context): DaoMaster.OpenHelper {
        return DaoMaster.DevOpenHelper(context, "pfm-db", null)
    }

    @Singleton
    @Provides
    open fun provideDAOMaster(openHelper: DaoMaster.OpenHelper): DaoMaster {
        return DaoMaster(openHelper.writableDatabase)
    }

    @Singleton
    @Provides
    open fun provideDAOSession(daoMaster: DaoMaster): DaoSession {
        return daoMaster.newSession()
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PFMSharedPref", Context.MODE_PRIVATE)
    }
}