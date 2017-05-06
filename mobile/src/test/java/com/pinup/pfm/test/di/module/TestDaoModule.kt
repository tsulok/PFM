package com.pinup.pfm.test.di.module

import android.content.Context
import android.content.SharedPreferences
import com.pinup.pfm.di.module.DaoModule
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.model.database.DaoMaster
import com.pinup.pfm.model.database.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI module for testing
 */
class TestDaoModule: DaoModule() {

    override fun provideOpenHelper(context: Context): DaoMaster.OpenHelper {
        return DaoMaster.DevOpenHelper(context, null, null)
    }
}