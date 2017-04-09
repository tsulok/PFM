package com.pinup.pfm.domain.manager.sync

import com.orhanobut.logger.Logger
import com.pinup.pfm.domain.event.TransactionUpdatedEvent
import com.pinup.pfm.domain.repository.manager.transaction.ITransactionRepository
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager for handling sync processes
 */
@Singleton
class SyncManager
@Inject constructor(private val transactionInteractor: ITransactionInteractor,
                    private val eventBus: EventBus,
                    private val transactionDaoManager: ITransactionRepository): ISyncManager {

    private var syncIsInProgress = false

    override fun uploadUnsyncedTransactions() {

        if (syncIsInProgress) {
            Logger.d("A sync is already in progress")
            return
        }

        syncIsInProgress = true

        val unsyncedTransactions = transactionDaoManager.loadUnsyncedTransactions()
        if (unsyncedTransactions.isEmpty()) {
            Logger.d("There is no item needed to be uploaded")
            syncIsInProgress = false
            return
        }

        Logger.d("Sync started")
        transactionInteractor.uploadTransactions(unsyncedTransactions)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doFinally { syncIsInProgress = false }
                .subscribe ({
                    eventBus.post(TransactionUpdatedEvent())
                    Logger.d("Sync finished with success")
                }, {
                    Logger.d("Sync finished with failure")
                })
    }
}

interface ISyncManager {
    /**
     * Schedule a transaction upload
     */
    fun uploadUnsyncedTransactions()
}