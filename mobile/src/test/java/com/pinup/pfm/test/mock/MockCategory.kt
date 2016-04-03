package com.pinup.pfm.test.mock

import com.pinup.pfm.model.database.Category

/**
 * Mock category instances
 */
enum class MockCategory {
    instance;

    val category: Category = Category("Default", "", "Category Default", 0, null, null)
    val category1: Category = Category("1", "", "Category 1", 1, null, null)
    val category1_1: Category = Category("1", "", "Category 1_1", 3, null, null)
    val category2: Category = Category("2", "", "Category 2", 2, null, null)
    val category3: Category = Category("3", "", "Category 3", 5, null, null)
}