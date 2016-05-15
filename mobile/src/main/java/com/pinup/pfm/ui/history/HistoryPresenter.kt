package com.pinup.pfm.ui.history

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.model.transaction.ITransactionHistory
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for History
 */
class HistoryPresenter : BasePresenter<HistoryScreen> {

    @Inject lateinit var transactionInteractor: TransactionInteractor

    constructor() : super() {
        PFMApplication.injector.inject(this)
    }

    fun loadSavedTransaction(transactionHistory: ITransactionHistory) {
        screen?.loadSavedTransaction(transactionHistory.getTransaction())
    }
}