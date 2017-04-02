package com.pinup.pfm.domain.repository.manager.transaction

import com.pinup.pfm.domain.repository.manager.BaseDaoManager
import com.pinup.pfm.model.database.CategoryDao
import com.pinup.pfm.model.database.DaoSession
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.database.TransactionDao
import java.util.*
import javax.inject.Inject

/**
 * Dao manager for categories
 */

class TransactionDaoManager @Inject constructor(val daoSession: DaoSession)
    : BaseDaoManager<TransactionDao, Transaction>(daoSession.transactionDao), ITransactionRepository {

    override fun loadByServerId(serverId: String): Transaction? {
        return createQuery()
                .where(TransactionDao.Properties.ServerId.eq(serverId))
                .unique()
    }

    override fun loadTransactionsAfter(date: Date): List<Transaction> {
        return createQuery()
                .where(TransactionDao.Properties.Date.gt(date))
                .orderDesc(TransactionDao.Properties.Date)
                .list()
    }
}