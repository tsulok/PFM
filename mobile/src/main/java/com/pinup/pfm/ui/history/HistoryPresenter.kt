package com.pinup.pfm.ui.history

import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.model.transaction.ITransactionHistory
import com.pinup.pfm.common.ui.core.BasePresenter
import javax.inject.Inject

/**
 * Presenter for History
 */
class HistoryPresenter @Inject constructor(val transactionInteractor: TransactionInteractor) : BasePresenter<HistoryScreen>() {

    fun loadSavedTransaction(transactionHistory: ITransactionHistory) {
        screen?.loadSavedTransaction(transactionHistory.getTransaction())
    }
}