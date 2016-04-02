package com.pinup.pfm.interactor.category

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.CategoryDao
import com.pinup.pfm.model.database.DaoSession
import java.util.*
import javax.inject.Inject

/**
 * Interactor for categories
 */
class CategoryInteractor {

    // TODO inject category api

    @Inject
    lateinit var daoSession: DaoSession

    constructor() {
        PFMApplication.injector.inject(this)
    }

    /**
     * Returns the list of the selectable categories ordered by their order
     * Only non main categories are present
     * @return
     */
    fun listAllSelectableCategories(): List<Category> {
        return daoSession.categoryDao
                .queryBuilder()
                .where(CategoryDao.Properties.ParentCategoryId.isNotNull)
                .orderAsc(CategoryDao.Properties.Order)
                .list()
    }

    /**
     * Returns the list of the main categories ordered by their order
     * Only main categories are present
     * @return
     */
    fun listAllMainCategories(): List<Category> {
        return daoSession.categoryDao
                .queryBuilder()
                .where(CategoryDao.Properties.ParentCategoryId.isNull)
                .orderAsc(CategoryDao.Properties.Order)
                .list()
    }

    /**
     * Returns the selected category associated with the serverId
     * @param serverId The id on the server
     * @return the selected category associated with the serverId
     */
    fun getCategoryByServerId(serverId: String): Category? {
        return daoSession.categoryDao
                .queryBuilder()
                .where(CategoryDao.Properties.ServerId.eq(serverId))
                .unique()
    }

    /**
     * Create or update a category with the given arguments
     * @param serverId The id on the server
     * @param name The name of the category
     * @param order The order of the category
     * @param imageUri The image key of the category if any
     */
    fun createOrUpdateCategory(serverId: String, name: String, order: Int,
                               imageUri: String?) {
        var category = getCategoryByServerId(serverId)

        if (category == null) {
            category = Category(UUID.randomUUID().toString())
            category.serverId = serverId
        }

        category.name = name
        category.order = order
        category.imageUri = imageUri

        daoSession.categoryDao.insertOrReplace(category)
    }

    /**
     * Deletes the selected category
     * @param category The deletable category
     */
    fun deleteCategory(category: Category?) {
        if (category != null) {
            daoSession.categoryDao.delete(category)
        }
    }

    // TODO create graph between parent & children
    // TODO get correct category image
}