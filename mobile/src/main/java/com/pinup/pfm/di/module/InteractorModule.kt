package com.pinup.pfm.di.module

import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import dagger.Binds
import dagger.Module

/**
 * DI modules for Interactors
 */
@Module
abstract class InteractorModule {
    @Binds abstract fun categoryInteractor(interactor: CategoryInteractor): ICategoryInteractor
    @Binds abstract fun transactionInteractor(interactor: TransactionInteractor): ITransactionInteractor
}