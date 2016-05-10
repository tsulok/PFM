package com.pinup.pfm.model.transaction

import com.pinup.pfm.model.database.Transaction
import java.util.*

/**
 * Data for transaction history items
 */
data class TransactionHistory(private var name: String,
                              private var date: Date,
                              private var amount: Double,
                              private var currency: String) : ITransactionHistory {

    constructor(transaction: Transaction) : this(transaction.name, transaction.date, transaction.amount, transaction.currency)

    override fun updateTransaction(transaction: Transaction) {
        name = transaction.name
        date = transaction.date
        amount = transaction.amount
        currency = transaction.currency
    }

    override fun getName(): String {
        return name
    }

    override fun getDate(): Date {
        return date
    }

    override fun getAmount(): Double {
        return amount
    }

    override fun getCurrency(): String {
        return currency
    }
}