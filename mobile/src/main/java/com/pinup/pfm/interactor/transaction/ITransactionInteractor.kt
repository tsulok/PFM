package com.pinup.pfm.interactor.transaction

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*

/**
 * Interactor for transactions
 */
interface ITransactionInteractor {

    /**
     * Returns the list of saved transactions ordered by date
     * @return the list of saved transactions ordered by date
     */
    fun listAllTransaction(): List<Transaction>

    /**
     * Returns the selected transaction associated with the local id
     * @param id The local id
     * @return the selected transaction associated with the local id
     */
    fun getTransactionById(id: String): Transaction?

    /**
     * Returns the selected transaction associated with the serverId
     * @param serverId The id on the server
     * @return the selected transaction associated with the serverId
     */
    fun getTransactionByServerId(serverId: String): Transaction?

    /**
     * Creates a new transaction with the mandatory parameters
     * @param name The name of the transaction
     * @param amount The amount of the transaction
     * @param currency The currency of the transaction
     * @param category The category of the transaction
     */
    fun createTransaction(name: String, amount: Double, currency: String,
                          category: Category?): Transaction

    /**
     * Create or updates a transaction with an entity
     * @param transaction The transaction entity
     */
    fun createOrUpdateTransaction(transaction: Transaction?)

    /**
     * Updates the transaction's server id
     * @param serverId The id on the server
     */
    fun updateTransactionServerId(transaction: Transaction, serverId: String)

    /**
     * Updates the transaction's last sync date to current
     * @param transaction The stored transaction
     */
    fun updateTransactionSynced(transaction: Transaction)

    /**
     * Updates the transaction's date
     * @param transaction The stored transaction
     * @param date The new date
     */
    fun updateTransactionDate(transaction: Transaction, date: Date)

    /**
     * Updates the transaction's geolocation
     * @param transaction The stored transaction
     * @param location The new location
     */
    fun updateTransactionLocation(transaction: Transaction, location: LatLng)

    /**
     * Updates the transaction's image uri
     * @param transaction The stored transaction
     * @param imageUri The new image uri
     */
    fun updateTransactionImageUri(transaction: Transaction, imageUri: String?, isServerUri: Boolean = false)

    /**
     * Updates the transaction's amount with currency
     * @param transaction The stored transaction
     * @param amount The new amount
     * @param currency The new currency
     */
    fun updateTransactionAmount(transaction: Transaction, amount: Double, currency: Currency?)

    /**
     * Updates the transaction's name
     * @param transaction The stored transaction
     * @param name The new name
     */
    fun updateTransactionName(transaction: Transaction, name: String)

    /**
     * Updates the transaction's description
     * @param transaction The stored transaction
     * @param description The new description
     */
    fun updateTransactionDescription(transaction: Transaction, description: String)

    /**
     * Updates the transaction's tag
     * @param transaction The stored transaction
     * @param tag The new tag
     */
    fun updateTransactionTag(transaction: Transaction, tag: String)

    /**
     * Updates the transaction's category
     * @param transaction The stored transaction
     * @param category The new category
     */
    fun updateTransactionCategory(transaction: Transaction, category: Category?)

    /**
     * Deletes the selected transaction
     * @param transaction The deletable transaction
     */
    fun deleteTransaction(transaction: Transaction)

    /**
     * Fetch transactions from the network
     * @return DB stored transactions
     */
    fun fetchTransactionsFromRemote(): Observable<List<Transaction>>

    /**
     * Upload transactions to remote
     */
    fun uploadTransactions(transactions: List<Transaction>): Completable
}