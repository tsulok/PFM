package com.pinup.pfm.model.category

/**
 * Interface for listing categories
 */
interface ICategoryItem {

    fun getName(): String
    fun getOrder(): Int
    fun getIconUri(): String
}