package com.pinup.pfm.ui.input.container

import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.domain.manager.transaction.TransactionManager
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input container
 */
class InputContainerPresenter @Inject constructor(val categoryInteractor: CategoryInteractor,
                                                  val transactionInteractor: TransactionInteractor,
                                                  val transactionManager: TransactionManager)
    : BasePresenter<InputContainerScreen>() {

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