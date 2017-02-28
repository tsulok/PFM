package com.pinup.pfm.test.dao.category

import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.test.mock.MockCategory
import com.pinup.pfm.test.utils.BaseTest
import com.pinup.pfm.test.utils.RobolectricDaggerTestRunner
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Test for categories
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CategoryTests : BaseTest() {

    @Inject lateinit var categoryInteractor: ICategoryInteractor

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.applicationComponent as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory("1", "Test", 1)
        categoryInteractor.createOrUpdateCategory("delete", "Test for delete", 3)
        categoryInteractor.createOrUpdateCategory(MockCategory.instance.category3)
    }

    @Test()
    fun testCategoriesCreated() {
        Assert.assertEquals("Non 3 items presented in the database", 3, categoryInteractor.listAllCategories().size)
    }

    @Test
    fun testReadByServerId() {
        val category = categoryInteractor.getCategoryByServerId("1")
        val category2 = categoryInteractor.getCategoryByServerId("2")

        Assert.assertNotNull("Category is null", category)
        Assert.assertNull("Category is not null", category2)
    }

    @Test
    fun testUpdateByServerId() {
        categoryInteractor.createOrUpdateCategory("1", "Test 2", 2)

        val category = categoryInteractor.getCategoryByServerId("1")
        Assert.assertNotNull("Category is null", category)
        Assert.assertEquals("Test 2", category?.name)
        Assert.assertEquals(2, category?.order)
    }

    @Test
    fun testDelete() {
        val category = categoryInteractor.getCategoryByServerId("delete")
        Assert.assertNotNull("Category is null", category)

        categoryInteractor.deleteCategory(category)

        val categoryAfter = categoryInteractor.getCategoryByServerId("delete")
        Assert.assertNull("Category is not null after deletion", categoryAfter)
    }
}