package com.pinup.pfm.domain.repository.manager.transaction

import com.pinup.pfm.domain.repository.manager.BaseDaoManager
import com.pinup.pfm.model.database.CategoryDao
import com.pinup.pfm.model.database.DaoSession
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.database.TransactionDao
import de.greenrobot.dao.query.WhereCondition
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

    override fun loadUnsyncedTransactions(): List<Transaction> {
        return createQuery()
                .whereOr(TransactionDao.Properties.ServerId.isNull,
                        TransactionDao.Properties.ServerId.eq(""),
                        WhereCondition.StringCondition(
                                TransactionDao.Properties.LastSyncDate.columnName + " < "
                                        + TransactionDao.Properties.LastModifyDate.columnName))
                .list()
    }
}