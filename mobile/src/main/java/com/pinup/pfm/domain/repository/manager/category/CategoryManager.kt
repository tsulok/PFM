package com.pinup.pfm.domain.repository.manager.category

import com.pinup.pfm.domain.repository.manager.BaseDaoManager
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.CategoryDao
import com.pinup.pfm.model.database.DaoSession
import javax.inject.Inject

/**
 * Dao manager for categories
 */

class CategoryDaoManager @Inject constructor(val daoSession: DaoSession)
    : BaseDaoManager<CategoryDao, Category>(daoSession.categoryDao), ICategoryRepository {

    override fun listAllSelectableCategories(): List<Category> {
        return daoSession.categoryDao
                .queryBuilder()
                .where(CategoryDao.Properties.ParentCategoryId.isNotNull)
                .orderAsc(CategoryDao.Properties.Order)
                .list()
    }

    override fun listAllMainCategories(): List<Category> {
        return daoSession.categoryDao
                .queryBuilder()
                .where(CategoryDao.Properties.ParentCategoryId.isNull)
                .orderAsc(CategoryDao.Properties.Order)
                .list()
    }

    override fun loadByServerId(serverId: String?): Category? {

        if (serverId == null) {
            return null
        }

        return createQuery()
                .where(CategoryDao.Properties.ServerId.eq(serverId))
                .unique()
    }
}