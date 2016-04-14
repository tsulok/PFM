package com.pinup.pfm.model.category

/**
 * Data for category items
 */
data class CategoryItem(private val name: String, private var order: Int, private val iconUri: String) : ICategoryItem {

    override fun getName(): String {
        return this.name
    }

    override fun getOrder(): Int {
        return this.order
    }

    override fun getIconUri(): String {
        return this.iconUri
    }
}