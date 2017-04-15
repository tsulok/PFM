package com.pinup.pfm.interactor.category

import com.orhanobut.logger.Logger
import com.pinup.pfm.domain.network.dto.category.CategoryNetworkResponseModel
import com.pinup.pfm.domain.network.service.CategoryService
import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import com.pinup.pfm.model.database.Category
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashSet

/**
 * Interactor for categories
 */
class CategoryInteractor
@Inject constructor(val categoryDaoManager: ICategoryRepository,
                    val categoryService: CategoryService) : ICategoryInteractor {

    // TODO inject category api

    override fun listAllCategories(): List<Category> {
        return categoryDaoManager.listAllItems()
    }

    override fun listAllSelectableCategories(): List<Category> {
        val usedChildrenParents = listAllCategories()
                .filter { it.parent != null }
                .groupBy { it.parent }
        return listAllCategories().filter {
            !usedChildrenParents.contains(it)
        }
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
    override fun fetchCategoriesFromRemote(): Observable<List<Category>> {
        return categoryService.listCategories()
                .doOnNext { responseModel ->
                    responseModel.data?.let { items ->
                        storeCategories(items.filter { !it.isDeleted })
                        deleteCategories(items.filter { it.isDeleted })
                    }
                }.map { listAllCategories() }
    }

    private fun storeCategories(categoryDTOs: List<CategoryNetworkResponseModel>) {
        // Let save the categories first
        for (categoryDTO in categoryDTOs) {
            val category = CategoryMapper.ModelMapping.from(categoryDTO)
            categoryDaoManager.insertOrUpdate(category)

            for (childCategoryDTO in categoryDTO.children) {
                storeChildCategories(childCategoryDTO, category)
            }
        }

        Logger.d("Parent categories stored %1\$d", categoryDTOs.size)
    }

    private fun storeChildCategories(categoryDTO: CategoryNetworkResponseModel,
                                     parentCategory: Category) {
        val category = CategoryMapper.ModelMapping.from(categoryDTO)
        category.parent = parentCategory
        categoryDaoManager.insertOrUpdate(category)

        for (childCategoryDTO in categoryDTO.children) {
            storeChildCategories(childCategoryDTO, category)
        }
    }

    private fun deleteCategories(categoryDTOs: List<CategoryNetworkResponseModel>) {
        for (categoryDTO in categoryDTOs) {
            categoryDaoManager.loadByServerId(categoryDTO.id)?.let {
                categoryDaoManager.delete(it)
            }
        }
    }

    override fun getDefaultCategory(): Category {
        return categoryDaoManager.loadByServerId("44BE5C72-C18F-4A98-8879-73D004280DAA")
                ?: throw IllegalStateException("Default category not loaded")
    }
}

private class CategoryMapper {
    object ModelMapping {
        fun from(dto: CategoryNetworkResponseModel): Category {
            val category = Category(dto.id)
            category.serverId = dto.id
            category.imageUri = dto.imageUrl
            category.name = dto.name
            category.parentCategoryId = dto.parentId
            return category
        }
    }
}