package com.pinup.pfm.domain.manager.content

import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Manages content on the device
 */
class ContentManager
@Inject constructor(val transactionInteractor: ITransactionInteractor,
                    val categoryInteractor: ICategoryInteractor): IContentManager {

    override fun downloadContents(): Completable {
        val transactionCompletable = Completable.fromObservable(transactionInteractor.fetchTransactionsFromRemote())
        val categoriesCompletable = Completable.fromObservable(categoryInteractor.fetchCategoriesFromRemote())

        return Completable.concatArray(categoriesCompletable, transactionCompletable)
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