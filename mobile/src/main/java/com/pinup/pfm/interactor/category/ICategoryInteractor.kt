package com.pinup.pfm.interactor.category

import com.pinup.pfm.model.database.Category
import io.reactivex.Observable
import java.util.*

/**
 * Category interactor provided actions
 */
interface ICategoryInteractor {

    /**
     * @return all category items
     */
    fun listAllCategories(): List<Category>

    /**
     * Returns the list of the selectable categories ordered by their order
     * Only non main categories are present
     * @return the list of the selectable categories ordered by their order
     */
    fun listAllSelectableCategories(): List<Category>

    /**
     * Returns the list of the main categories ordered by their order
     * Only main categories are present
     * @return the list of the main categories ordered by their order
     */
    fun listAllMainCategories(): List<Category>

    /**
     * Returns the selected category associated with the serverId
     * @param serverId The id on the server
     * @return the selected category associated with the serverId
     */
    fun getCategoryByServerId(serverId: String): Category?

    /**
     * Create or update a category with the given arguments
     * @param serverId The id on the server
     * @param name The name of the category
     * @param order The order of the category
     * @param imageUri The image key of the category if any
     */
    fun createOrUpdateCategory(serverId: String, name: String, order: Int,
                               imageUri: String? = null): Category

    /**
     * Create or update a category
     * @param category A category entity
     */
    fun createOrUpdateCategory(category: Category?)

    /**
     * Deletes the selected category
     * @param category The deletable category
     */
    fun deleteCategory(category: Category?)

    /**
     * Deletes all the categories
     */
    fun deleteAllCategories()

    /**
     * Updates the category hierarchy additions
     */
    fun updateCategoryHierarchyAdditions(mapping: HashMap<Category, HashSet<Category>>)

    /**
     * Updates the category hierarch removals
     */
    fun updateCategoryHierarchyRemovals(mapping: HashMap<Category, HashSet<Category>>)

    /**
     * Creates test data
     */
    fun createTestData()

    /**
     * Fetch & store categories from remote
     */
    fun fetchCategoriesFromRemote(): Observable<List<Category>>
}