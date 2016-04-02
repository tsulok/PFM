package com.pinup.pfm.interactor.transaction

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.model.database.*
import java.util.*
import javax.inject.Inject

/**
 * Interactor for transactions
 */
class TransactionInteractor {

    // TODO inject transaction api

    @Inject lateinit var daoSession: DaoSession

    constructor() {
        PFMApplication.injector.inject(this)
    }

    /**
     * Returns the list of saved transactions ordered by date
     * @return the list of saved transactions ordered by date
     */
    fun listAllTransaction(): List<Transaction> {
        return daoSession.transactionDao
                .queryBuilder()
                .orderDesc(TransactionDao.Properties.Date)
                .list()
    }

    /**
     * Returns the selected transaction associated with the local id
     * @param id The local id
     * @return the selected transaction associated with the local id
     */
    fun getTransactionById(id: String): Transaction? {
        return daoSession.transactionDao.load(id)
    }

    /**
     * Returns the selected transaction associated with the serverId
     * @param serverId The id on the server
     * @return the selected transaction associated with the serverId
     */
    fun getTransactionByServerId(serverId: String): Transaction? {
        return daoSession.transactionDao
                .queryBuilder()
                .where(TransactionDao.Properties.ServerId.eq(serverId))
                .unique()
    }

    /**
     * Creates a new transaction with the mandatory parameters
     * @param name The name of the transaction
     * @param amount The amount of the transaction
     * @param currency The currency of the transaction
     * @param category The category of the transaction
     */
    fun createTransaction(name: String, amount: Double, currency: String,
                          category: Category) {

        var transaction = Transaction(UUID.randomUUID().toString())
        transaction.name = name
        transaction.amount = amount
        transaction.currency = currency
        transaction.category = category
        transaction.lastImageModifyDate = Date(0)
        transaction.lastImageSyncDate = Date(0)
        transaction.lastSyncDate = Date(0)
        transaction.lastModifyDate = Date()

        daoSession.transactionDao.insert(transaction)
    }

    // TODO add here multiple transaction creation from api

    /**
     * Updates the transaction's date
     * @param transaction The stored transaction
     * @param date The new date
     */
    fun updateTransactionDate(transaction: Transaction, date: Date) {
        transaction.date = date
        transaction.lastModifyDate = Date()
        daoSession.transactionDao.update(transaction)
    }

    /**
     * Updates the transaction's geolocation
     * @param transaction The stored transaction
     * @param location The new location
     */
    fun updateTransactionLocation(transaction: Transaction, location: LatLng) {
        transaction.latitude = location.latitude
        transaction.longitude = location.longitude
        transaction.lastModifyDate = Date()
        daoSession.transactionDao.update(transaction)
    }

    /**
     * Updates the transaction's image uri
     * @param transaction The stored transaction
     * @param imageUri The new image uri
     */
    fun updateTransactionImageUri(transaction: Transaction, imageUri: String?, isServerUri: Boolean = false) {
        transaction.imageUri = imageUri

        if (isServerUri) {
            transaction.lastImageSyncDate = Date()
        } else {
            transaction.lastImageModifyDate = Date()
        }

        daoSession.transactionDao.update(transaction)
    }

    /**
     * Updates the transaction's amount with currency
     * @param transaction The stored transaction
     * @param amount The new amount
     * @param currency The new currency
     */
    fun updateTransactionAmount(transaction: Transaction, amount: Double, currency: String) {
        transaction.amount = amount
        transaction.currency = currency
        transaction.lastModifyDate = Date()
        daoSession.transactionDao.update(transaction)
    }

    /**
     * Updates the transaction's name
     * @param transaction The stored transaction
     * @param name The new name
     */
    fun updateTransactionName(transaction: Transaction, name: String) {
        transaction.name = name
        transaction.lastModifyDate = Date()
        daoSession.transactionDao.update(transaction)
    }

    /**
     * Updates the transaction's description
     * @param transaction The stored transaction
     * @param description The new description
     */
    fun updateTransactionDescription(transaction: Transaction, description: String) {
        transaction.description = description
        transaction.lastModifyDate = Date()
        daoSession.transactionDao.update(transaction)
    }

    /**
     * Updates the transaction's tag
     * @param transaction The stored transaction
     * @param tag The new tag
     */
    fun updateTransactionTag(transaction: Transaction, tag: String) {
        transaction.tag = tag
        transaction.lastModifyDate = Date()
        daoSession.transactionDao.update(transaction)
    }

    /**
     * Updates the transaction's category
     * @param transaction The stored transaction
     * @param category The new category
     */
    fun updateTransactionCategory(transaction: Transaction, category: Category) {
        transaction.category = category
        transaction.lastModifyDate = Date()
        daoSession.transactionDao.update(transaction)
    }

    /**
     * Deletes the selected transaction
     * @param transaction The deletable transaction
     */
    fun deleteTransaction(transaction: Transaction) {
        daoSession.transactionDao.delete(transaction)
    }

}