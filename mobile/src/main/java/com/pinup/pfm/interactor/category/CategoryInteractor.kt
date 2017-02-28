package com.pinup.pfm.interactor.category

import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import com.pinup.pfm.model.database.Category
import java.util.*
import javax.inject.Inject

/**
 * Interactor for categories
 */
class CategoryInteractor @Inject constructor(val categoryDaoManager: ICategoryRepository) : ICategoryInteractor {

    // TODO inject category api

    override fun listAllCategories(): List<Category> {
        return categoryDaoManager.listAllItems()
    }

    override fun listAllSelectableCategories(): List<Category> {
        return categoryDaoManager.listAllSelectableCategories()
    }

    override fun listAllMainCategories(): List<Category> {
        return categoryDaoManager.listAllMainCategories()
    }

    override fun getCategoryByServerId(serverId: String): Category? {
        return categoryDaoManager.loadByServerId(serverId)
    }

    override fun createOrUpdateCategory(serverId: String, name: String, order: Int,
                               imageUri: String?): Category {
        var category = getCategoryByServerId(serverId)

        if (category == null) {
            category = Category(UUID.randomUUID().toString())
            category.serverId = serverId
        }

        category.name = name
        category.order = order
        category.imageUri = imageUri

        categoryDaoManager.insertOrUpdate(category)

        return category
    }

    override fun createOrUpdateCategory(category: Category?) {
        category?.let { categoryDaoManager.insertOrUpdate(it) }
    }

    override fun deleteCategory(category: Category?) {
        category?.let { categoryDaoManager.delete(it) }
    }

    override fun deleteAllCategories() {
        categoryDaoManager.removeAll()
    }

    override fun updateCategoryHierarchyAdditions(mapping: HashMap<Category, HashSet<Category>>) {
        for ((parentCategory, value) in mapping) {

            var children = parentCategory.children

            for (childCategory in value) {

                childCategory.parentCategoryId = parentCategory.id
                categoryDaoManager.update(childCategory)

                if (children.size == 0) {
                    parentCategory.resetChildren()
                    children = parentCategory.children
                } else {
                    children.add(childCategory)
                }
            }

            categoryDaoManager.update(parentCategory)
        }
    }

    override fun updateCategoryHierarchyRemovals(mapping: HashMap<Category, HashSet<Category>>) {
        for ((parentCategory, value) in mapping) {

            for (childCategory in value) {
                childCategory.parent = null
                parentCategory.children.remove(childCategory)
                categoryDaoManager.update(childCategory)
            }

            categoryDaoManager.update(parentCategory)
        }
    }

    //endregion

    override fun createTestData() {
        if (listAllCategories().isEmpty()) {
            val parentCategory = createOrUpdateCategory("0", "Main Category", 0)
            val children = HashSet<Category>()

            for (i in 1..12) {
                var iconName = ""
                when (i % 4) {
                    0 -> iconName = "ic_category_health"
                    1 -> iconName = "ic_category_food"
                    2 -> iconName = "ic_category_fun"
                    3 -> iconName = "ic_category_transportation"
                }
                val child = createOrUpdateCategory("$i", "Teszt $i", i, iconName)
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