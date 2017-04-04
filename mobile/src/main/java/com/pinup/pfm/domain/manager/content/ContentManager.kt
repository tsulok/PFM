package com.pinup.pfm.domain.manager.content

import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Manages content on the device
 */
class ContentManager
@Inject constructor(val transactionInteractor: ITransactionInteractor,
                    val categoryInteractor: ICategoryInteractor): IContentManager {

    override fun downloadContents(): Completable {
        val transactionObservable = transactionInteractor.fetchTransactionsFromRemote()
        val categoriesObservable = categoryInteractor.fetchCategoriesFromRemote()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uploadUnsyncedTransactions(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}