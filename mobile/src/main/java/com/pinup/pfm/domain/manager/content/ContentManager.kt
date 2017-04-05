package com.pinup.pfm.domain.manager.content

import com.pinup.pfm.domain.manager.preferences.SharedPreferencesManager
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject

/**
 * Manages content on the device
 */
class ContentManager
@Inject constructor(val transactionInteractor: ITransactionInteractor,
                    val categoryInteractor: ICategoryInteractor,
                    val preferencesManager: SharedPreferencesManager) : IContentManager {

    companion object SharedPrefKey {
        val PREF_LAST_SYNC_TIME = "com.pfm.last.sync.time"
    }

    override fun downloadContents(): Completable {
        val transactionCompletable = Completable.fromObservable(transactionInteractor.fetchTransactionsFromRemote())
        val categoriesCompletable = Completable.fromObservable(categoryInteractor.fetchCategoriesFromRemote())

        return Completable.concatArray(categoriesCompletable, transactionCompletable)
                .doOnComplete { preferencesManager.setLongPreference(SharedPrefKey.PREF_LAST_SYNC_TIME, System.currentTimeMillis()) }
    }

    override fun uploadUnsyncedTransactions(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSyncTime(): Date? {
        val lastSyncTime = preferencesManager.getLongPreference(SharedPrefKey.PREF_LAST_SYNC_TIME, 0)
        if (lastSyncTime == 0L) {
            return null
        }

        return Date(lastSyncTime)
    }
}

interface IContentManager {

    /**
     * Download necessary contents from the server
     * Transactions & categories
     */
    fun downloadContents(): Completable

    /**
     * Upload unsynced transactions
     */
    fun uploadUnsyncedTransactions(): Completable

    /**
     * Returns the last sync time
     */
    fun getLastSyncTime(): Date?
}