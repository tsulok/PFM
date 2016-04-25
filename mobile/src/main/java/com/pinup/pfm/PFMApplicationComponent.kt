package com.pinup.pfm

import com.pinup.pfm.interactor.CommunicationModule
import com.pinup.pfm.interactor.DaoModule
import com.pinup.pfm.interactor.InteractorModule
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.interactor.utils.StorageInteractor
import com.pinup.pfm.presenter.PresenterModule
import com.pinup.pfm.ui.ActivityModule
import com.pinup.pfm.ui.MainActivity
import com.pinup.pfm.ui.UIModule
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.input.container.InputContainerPresenter
import com.pinup.pfm.ui.input.main.InputMainPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@Singleton
@Component(modules = arrayOf(UIModule::class, InteractorModule::class, DaoModule::class, CommunicationModule::class, PresenterModule::class) )
interface PFMApplicationComponent {

    fun inject(activityModule: ActivityModule): PFMActivityComponent
    fun inject(interactorModule: InteractorModule): Unit

    fun inject(categoryInteractor: CategoryInteractor): Unit
    fun inject(currencyInteractor: CurrencyInteractor): Unit
    fun inject(transactionInteractor: TransactionInteractor): Unit
    fun inject(storageInteractor: StorageInteractor): Unit

    fun inject(inputContainerPresenter: InputContainerPresenter): Unit
    fun inject(inputMainPresenter: InputMainPresenter): Unit
}