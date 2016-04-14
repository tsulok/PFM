package com.pinup.pfm.interactor.category

import android.content.Context
import android.graphics.drawable.Drawable
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.CategoryDao
import com.pinup.pfm.model.database.DaoSession
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import java.util.*
import javax.inject.Inject

/**
 * Interactor for categories
 */
class CategoryInteractor {

    // TODO inject category api

    @Inject lateinit var daoSession: DaoSession
    @Inject lateinit var context: Context

    constructor() {
        PFMApplication.injector.inject(this)
    }

    //region DAO
    /**
     * Returns all category items
     * @return all category items
     */
    fun listAllCategories(): List<Category> {
        return daoSession.categoryDao.loadAll()
    }

    /**
     * Returns the list of the selectable categories ordered by their order
     * Only non main categories are present
     * @return the list of the selectable categories ordered by their order
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
     * @return the list of the main categories ordered by their order
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
                               imageUri: String? = null): Category {
        var category = getCategoryByServerId(serverId)

        if (category == null) {
            category = Category(UUID.randomUUID().toString())
            category.serverId = serverId
        }

        category.name = name
        category.order = order
        category.imageUri = imageUri

        daoSession.categoryDao.insertOrReplace(category)

        return category
    }

    /**
     * Create or update a category
     * @param category A category entity
     */
    fun createOrUpdateCategory(category: Category?) {
        if (category != null) {
            daoSession.categoryDao.insertOrReplace(category)
        }
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

    /**
     * Deletes all the categories
     */
    fun deleteAllCategories() {
        daoSession.categoryDao.deleteAll()
    }

    /**
     * Updates the category hierarchy additions
     */
    fun updateCategoryHierarchyAdditions(mapping: HashMap<Category, HashSet<Category>>) {
        for (mapEntry in mapping) {
            val parentCategory: Category = mapEntry.key

            var children = parentCategory.children

            for (childCategory in mapEntry.value) {

                childCategory.parentCategoryId = parentCategory.id
                daoSession.update(childCategory)

                if (children.size == 0) {
                    parentCategory.resetChildren()
                    children = parentCategory.children
                } else {
                    children.add(childCategory)
                }
            }

            daoSession.update(parentCategory)
        }
    }

    /**
     * Updates the category hierarch removals
     */
    fun updateCategoryHierarchyRemovals(mapping: HashMap<Category, HashSet<Category>>) {
        for (mapEntry in mapping) {
            val parentCategory: Category = mapEntry.key

            for (childCategory in mapEntry.value) {
                childCategory.parent = null
                parentCategory.children.remove(childCategory)
                daoSession.update(childCategory)
            }

            daoSession.update(parentCategory)
        }
    }

    //endregion

    fun createTestData() {
        if (listAllCategories().size == 0) {
            val parentCategory = createOrUpdateCategory("0", "Main Category", 0)
            val children = HashSet<Category>()

            for (i in 1..13) {
                val child = createOrUpdateCategory("$i", "Teszt $i", i, "ic_restaurant")
                children.add(child)
            }

            val mapping = HashMap<Category, HashSet<Category>>()
            mapping[parentCategory] = children
            updateCategoryHierarchyAdditions(mapping)
        }
    }

    // TODO create graph between parent & children
    // TODO get correct category image
}