package com.pinup.pfm

import com.pinup.pfm.di.component.PFMApplicationComponent
import com.pinup.pfm.di.module.InteractorModule
import com.pinup.pfm.di.module.PresenterModule
import com.pinup.pfm.test.dao.category.CategoryDeletionTests
import com.pinup.pfm.test.dao.category.CategoryTests
import com.pinup.pfm.test.dao.transaction.TransactionDeletionTest
import com.pinup.pfm.test.dao.transaction.TransactionTests
import com.pinup.pfm.test.interactor.CurrencyInteractorTest
import com.pinup.pfm.test.presenter.mainInput.InputMainPresenterTests
import dagger.Component
import javax.inject.Singleton

/**
 * Component for supported DI components in test environment
 */
@Singleton
@Component(modules = arrayOf(InteractorModule::class, TestModule::class, PresenterModule::class) )
interface TestComponent : PFMApplicationComponent {


    //region DAO
    fun inject(categoryTests: CategoryTests)
    fun inject(categoryDeletionTests: CategoryDeletionTests)

    fun inject(transactionTests: TransactionTests)
    fun inject(transactionDeletionTest: TransactionDeletionTest)
    //endregion

    //region Interactor
    fun inject(currencyInteractorTest: CurrencyInteractorTest)
    //endregion

    //region Presenters
    fun inject(inputMainPresenterTests: InputMainPresenterTests)
    //endregion
}