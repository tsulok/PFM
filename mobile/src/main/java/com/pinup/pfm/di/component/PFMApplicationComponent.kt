package com.pinup.pfm.di.component

import com.pinup.pfm.di.module.ActivityModule
import com.pinup.pfm.di.module.UIModule
import com.pinup.pfm.di.module.UtilityModule
import com.pinup.pfm.di.module.DaoModule
import com.pinup.pfm.di.module.InteractorModule
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.model.database.DaoSession
import com.pinup.pfm.di.module.PresenterModule
import dagger.Component
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@Singleton
@Component(modules = arrayOf(UIModule::class, InteractorModule::class, DaoModule::class,
        PresenterModule::class, UtilityModule::class) )
interface PFMApplicationComponent {

    fun inject(activityModule: ActivityModule): PFMActivityComponent

    fun currencyInteractor(): CurrencyInteractor
    fun transacionInteractor(): TransactionInteractor
    fun currentTransactionInteractor(): CurrentTransactionInteractor
    fun daoSession(): DaoSession
}
