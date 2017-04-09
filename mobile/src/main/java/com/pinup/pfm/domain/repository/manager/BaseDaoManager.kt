package com.pinup.pfm.domain.repository.manager

import android.support.annotation.Nullable
import de.greenrobot.dao.AbstractDao
import de.greenrobot.dao.query.QueryBuilder

/**
 * Base manager for data access
 */

open class BaseDaoManager<out Dao : AbstractDao<T, String>, T>(val dao: Dao) : IBaseRepository<T> {

    @Nullable
    override fun loadById(id: String): T? {
        return dao.load(id)
    }

    override fun insertOrUpdate(item: T) {
        dao.insertOrReplace(item)
    }

    override fun update(item: T) {
        dao.update(item)
    }

    override fun delete(item: T) {
        dao.delete(item)
    }

    override fun listAllItems(): List<T> {
        return dao.loadAll()
    }

    override fun removeAll() {
        dao.deleteAll()
    }

    override fun listItems(ids: List<String>): List<T> {
        return createQuery()
                .where(dao.pkProperty.`in`(ids))
                .list()
    }

    override fun removeItems(ids: List<String>) {
        createQuery()
                .where(dao.pkProperty.`in`(ids))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities()
    }

    protected fun createQuery(): QueryBuilder<T> {
        return dao.queryBuilder()
    }
}