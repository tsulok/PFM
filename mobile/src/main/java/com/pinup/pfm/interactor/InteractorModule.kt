package com.pinup.pfm.interactor

import android.content.Context
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.model.database.DaoMaster
import com.pinup.pfm.model.database.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI modules for Interactors
 */
@Module
class InteractorModule(private val context: Context) {


    @Singleton
    @Provides
    fun provideCategoryInteractor(): CategoryInteractor {
        return CategoryInteractor()
    }

    @Singleton
    @Provides
    fun provideDAOSession(): DaoSession {
        val openHelper = DaoMaster.DevOpenHelper(context, "pfm-db", null)
        val daoMaster = DaoMaster(openHelper.writableDatabase)
        return daoMaster.newSession()
    }
}