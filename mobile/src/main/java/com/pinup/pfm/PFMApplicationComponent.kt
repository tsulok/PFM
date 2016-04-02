package com.pinup.pfm

import com.pinup.pfm.interactor.InteractorModule
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.ui.MainActivity
import com.pinup.pfm.ui.UIModule
import dagger.Component
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@Singleton
@Component(modules = arrayOf(UIModule::class, InteractorModule::class) )
interface PFMApplicationComponent {

    fun inject(activity: MainActivity): Unit

    fun inject(interactorModule: InteractorModule): Unit

    fun inject(categoryInteractor: CategoryInteractor): Unit
    fun inject(transactionInteractor: TransactionInteractor): Unit
}