package com.pinup.pfm.model.transaction

import java.util.*

/**
 * Interface for listing history
 */
interface ITransactionHistory {

    fun getName(): String
    fun getDate(): Date
    fun getAmount(): Double
    fun getCurrency(): String
}