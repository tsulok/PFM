package com.pinup.pfm.domain.repository.manager.category

import com.pinup.pfm.domain.repository.manager.IBaseRepository
import com.pinup.pfm.model.database.Category

/**
 * Category repository interface
 */

interface ICategoryRepository: IBaseRepository<Category> {

    /**
     * @return The desired item associated with the server id or nil
     */
    fun loadByServerId(serverId: String): Category?

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
}