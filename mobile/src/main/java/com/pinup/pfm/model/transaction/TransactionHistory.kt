package com.pinup.pfm.model.transaction

import com.pinup.pfm.model.database.Transaction
import java.util.*

/**
 * Data for transaction history items
 */
data class TransactionHistory(private var transaction: Transaction) : ITransactionHistory {

    override fun getTransaction(): Transaction {
        return transaction
    }

    override fun getName(): String {
        return transaction.name
    }

    override fun getDate(): Date {
        return transaction.date
    }

    override fun getAmount(): Double {
        return transaction.amount
    }

    override fun getCurrency(): String {
        return transaction.currency
    }

    override fun updateTransaction(transaction: Transaction) {
        this.transaction = transaction
    }

    override fun isSynced(): Boolean {
        return transaction.serverId != null && !transaction.serverId.isEmpty()
                && (transaction.lastSyncDate != null && transaction.lastModifyDate != null
                && transaction.lastSyncDate.after(transaction.lastModifyDate))
    }
}