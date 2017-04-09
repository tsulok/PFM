package com.pinup.pfm.domain.event

import com.pinup.pfm.model.database.Transaction

/**
 * Event for notifying transactions being updated
 */
class TransactionUpdatedEvent()

/**
 * Event for notifying a transaction has been uploaded
 */
data class TransactionSyncCompletedEvent(val transaction: Transaction)