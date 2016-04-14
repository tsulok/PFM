package com.pinup.pfm.model.transaction

import java.util.*

/**
 * Data for transaction history items
 */
data class TransactionHistory(private val name: String,
                              private val date: Date,
                              private val amount: Double,
                              private val currency: String) : ITransactionHistory {

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