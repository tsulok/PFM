package com.pinup.pfm.model.transaction

import com.pinup.pfm.model.database.Transaction

/**
 * Interface for transaction interaction actions
 */
interface OnTransactionInteractionListener {
    fun onTransactionAdded(transaction: Transaction)
    fun onTransactionEdited(transaction: Transaction)
    fun onTransactionOpened(transaction: Transaction)
    fun onTransactionDeleted(transaction: Transaction)
}