package com.pinup.pfm.interactor

import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI modules for Interactors
 */
@Module
class InteractorModule {

    @Singleton
    @Provides
    fun provideCategoryInteractor(): CategoryInteractor {
        return CategoryInteractor()
    }

    @Singleton
    @Provides
    fun provedTransactionInteractor(): TransactionInteractor {
        return TransactionInteractor()
    }
}