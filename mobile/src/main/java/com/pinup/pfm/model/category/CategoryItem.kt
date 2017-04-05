package com.pinup.pfm.model.category

import com.pinup.pfm.model.database.Category

/**
 * Data for category items
 */
data class CategoryItem(private val category: Category) : ICategoryItem {

    override fun getCategory(): Category {
        return category
    }

    override fun getName(): String {
        return category.name
    }

    override fun getOrder(): Int {
        return category.order
    }

    override fun getIconUri(): String {
        return category.imageUri ?: "ic_category_food"
    }

    override fun getCategoryId(): String {
        return category.id
    }
}