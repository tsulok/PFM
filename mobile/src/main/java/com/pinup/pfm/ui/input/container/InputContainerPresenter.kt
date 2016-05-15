package com.pinup.pfm.ui.input.container

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input container
 */
class InputContainerPresenter : BasePresenter<InputContainerScreen> {

    @Inject lateinit var categoryInteractor: CategoryInteractor
    @Inject lateinit var transactionInteractor: TransactionInteractor
    @Inject lateinit var currentTransactionInteractor: CurrentTransactionInteractor

    constructor() : super() {
        PFMApplication.injector.inject(this)
    }

    /**
     * Navigate to charts screen
     */
    fun navigateToCharts() {
        screen?.navigateToCharts()
    }

    /**
     * Navigate to settings screen
     */
    fun navigateToSettings() {
        screen?.navigateToSettings()
    }

    /**
     * Opens transaction history screen
     */
    fun openTransactionHistory() {
        screen?.openTransactionHistory()
    }
}