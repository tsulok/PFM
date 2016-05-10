package com.pinup.pfm.model.transaction

import com.pinup.pfm.model.database.Transaction
import java.util.*

/**
 * Interface for listing history
 */
interface ITransactionHistory {

    fun getName(): String
    fun getDate(): Date
    fun getAmount(): Double
    fun getCurrency(): String

    fun updateTransaction(transaction: Transaction)
}