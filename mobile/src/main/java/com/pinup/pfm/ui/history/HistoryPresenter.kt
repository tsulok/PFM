package com.pinup.pfm.ui.history

import com.orhanobut.logger.Logger
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.model.transaction.ITransactionHistory
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for History
 */
class HistoryPresenter @Inject constructor(val transactionInteractor: TransactionInteractor) : BasePresenter<HistoryScreen>() {

    fun fetchItemsFromRemote() {
        transactionInteractor.fetchTransactionsFromRemote()
                .subscribe({
                    Logger.d("success")
                }, { e ->
                    Logger.d("failure")
                })
    }

    fun loadSavedTransaction(transactionHistory: ITransactionHistory) {
        screen?.loadSavedTransaction(transactionHistory.getTransaction())
    }
}