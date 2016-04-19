package com.pinup.pfm.interactor

import android.content.Context
import android.content.SharedPreferences
import com.pinup.pfm.model.database.DaoMaster
import com.pinup.pfm.model.database.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI modules for Dao
 */
@Module
class DaoModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDAOSession(): DaoSession {
        val openHelper = DaoMaster.DevOpenHelper(context, "pfm-db", null)
        val daoMaster = DaoMaster(openHelper.writableDatabase)
        return daoMaster.newSession()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("PFMSharedPref", Context.MODE_PRIVATE)
    }
}