package com.pinup.pfm.di.module

import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.social.facebook.FacebookInteractor
import com.pinup.pfm.interactor.social.facebook.IFacebookInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.interactor.utils.ICurrencyInteractor
import com.pinup.pfm.interactor.utils.IStorageInteractor
import com.pinup.pfm.interactor.utils.StorageInteractor
import dagger.Binds
import dagger.Module

/**
 * DI modules for Interactors
 */
@Module
abstract class InteractorModule {
    @Binds abstract fun categoryInteractor(interactor: CategoryInteractor): ICategoryInteractor
    @Binds abstract fun transactionInteractor(interactor: TransactionInteractor): ITransactionInteractor
    @Binds abstract fun currencyInteractor(interactor: CurrencyInteractor): ICurrencyInteractor
    @Binds abstract fun storageInteractor(interactor: StorageInteractor): IStorageInteractor
    @Binds abstract fun facebookInteractor(interactor: FacebookInteractor): IFacebookInteractor
}