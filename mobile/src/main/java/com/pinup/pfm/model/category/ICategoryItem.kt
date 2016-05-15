package com.pinup.pfm.model.category

import com.pinup.pfm.model.database.Category

/**
 * Interface for listing categories
 */
interface ICategoryItem {

    fun getCategory(): Category
    fun getCategoryId(): String
    fun getName(): String
    fun getOrder(): Int
    fun getIconUri(): String
}