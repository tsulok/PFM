package com.pinup.pfm.interactor.transaction

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.domain.network.dto.transaction.TransactionItemDTO
import com.pinup.pfm.domain.network.service.TransactionService
import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import com.pinup.pfm.domain.repository.manager.transaction.ITransactionRepository
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.Transaction
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

/**
 * Interactor for transactions
 */
class TransactionInteractor
@Inject constructor(val transactionDaoManager: ITransactionRepository,
                    val transactionService: TransactionService,
                    val categoryRepository: ICategoryRepository) : ITransactionInteractor {

    override fun listAllTransaction(): List<Transaction> {
        return transactionDaoManager.listAllItems()
    }

    override fun getTransactionById(id: String): Transaction? {
        return transactionDaoManager.loadById(id)
    }

    override fun getTransactionByServerId(serverId: String): Transaction? {
        return transactionDaoManager.loadByServerId(serverId)
    }

    override fun createTransaction(name: String, amount: Double, currency: String,
                                   category: Category?): Transaction {

        val transaction = Transaction(UUID.randomUUID().toString())
        transaction.name = name
        transaction.amount = amount
        transaction.currency = currency

        if (category != null) {
            transaction.category = category
        }
        transaction.date = Date() // Created now
        transaction.lastImageModifyDate = Date(0) // No image associated yet
        transaction.lastImageSyncDate = Date(0) // No sync happened yet
        transaction.lastSyncDate = Date(0) // No sync happened yet
        transaction.lastModifyDate = Date() // Modified now

        transactionDaoManager.insertOrUpdate(transaction)

        return transaction
    }

    // TODO add here multiple transaction creation from api

    override fun createOrUpdateTransaction(transaction: Transaction?) {
        transaction?.let { transactionDaoManager.insertOrUpdate(transaction) }
    }

    override fun updateTransactionServerId(transaction: Transaction, serverId: String) {
        transaction.serverId = serverId
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionSynced(transaction: Transaction) {
        transaction.lastSyncDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionDate(transaction: Transaction, date: Date) {
        transaction.date = date
        transaction.lastModifyDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionLocation(transaction: Transaction, location: LatLng) {
        transaction.latitude = location.latitude
        transaction.longitude = location.longitude
        transaction.lastModifyDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionImageUri(transaction: Transaction, imageUri: String?, isServerUri: Boolean) {
        transaction.imageUri = imageUri

        if (isServerUri) {
            transaction.lastImageSyncDate = Date()
        } else {
            transaction.lastImageModifyDate = Date()
        }

        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionAmount(transaction: Transaction, amount: Double, currency: Currency?) {
        transaction.amount = amount
        transaction.currency = currency?.currencyCode
        transaction.lastModifyDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionName(transaction: Transaction, name: String) {
        transaction.name = name
        transaction.lastModifyDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionDescription(transaction: Transaction, description: String) {
        transaction.description = description
        transaction.lastModifyDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionTag(transaction: Transaction, tag: String) {
        transaction.tag = tag
        transaction.lastModifyDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun updateTransactionCategory(transaction: Transaction, category: Category?) {
        transaction.category = category
        transaction.lastModifyDate = Date()
        transactionDaoManager.update(transaction)
    }

    override fun deleteTransaction(transaction: Transaction) {
        transactionDaoManager.delete(transaction)
    }

    override fun fetchTransactionsFromRemote(): Observable<List<Transaction>> {
        return transactionService.listTransactions()
                .doOnNext { responseWrapper ->
                    responseWrapper.data?.let { items ->
                        storeTransactions(items.filter { !it.isDeleted })
                        deleteTransactions(items.filter { it.isDeleted })
                    }
                }
                .map { listAllTransaction() }
    }

    private fun storeTransactions(transactions: List<TransactionItemDTO>) {
        for (transactionDto in transactions) {
            val transaction = TransactionMapper.ModelMapper.from(transactionDto)
            categoryRepository.loadByServerId(transactionDto.category?.id)?.let {
                transaction.category = it
                transaction.categoryId = it.serverId
            }

            transactionDaoManager.insertOrUpdate(transaction)
        }
    }

    private fun deleteTransactions(transaction: List<TransactionItemDTO>) {
        for (transactionItemDTO in transaction) {
            transactionDaoManager.loadByServerId(transactionItemDTO.serverId)?.let {
                transactionDaoManager.delete(it)
            }
        }
    }
}

private class TransactionMapper {
    object ModelMapper {
        fun from(dto: TransactionItemDTO): Transaction {
            val transaction = Transaction(dto.serverId)
            transaction.serverId = dto.serverId
            transaction.amount = dto.amount
            transaction.currency = dto.currency
            transaction.date = dto.date
            transaction.description = dto.description
            transaction.imageUri = dto.imageUrl
            transaction.lastSyncDate = Date()
            transaction.lastImageSyncDate = Date()
            transaction.latitude = dto.latitude
            transaction.longitude = dto.longitude
            transaction.name = dto.name
            return transaction
        }
    }
}