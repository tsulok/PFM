package com.pinup.pfm.domain.repository.manager.transaction

import com.pinup.pfm.domain.repository.manager.IBaseRepository
import com.pinup.pfm.model.database.Transaction
import java.util.*

/**
 * Category repository interface
 */

interface ITransactionRepository : IBaseRepository<Transaction> {

    /**
     * @return The desired item associated with the server id or nil
     */
    fun loadByServerId(serverId: String): Transaction?

    /**
     * Load transactions created after date
     */
    fun loadTransactionsAfter(date: Date): List<Transaction>
}