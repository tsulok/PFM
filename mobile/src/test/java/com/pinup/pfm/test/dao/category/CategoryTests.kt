package com.pinup.pfm.test.dao.category

import android.R
import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.test.mock.MockCategory
import com.pinup.pfm.test.utils.BaseTest
import com.pinup.pfm.test.utils.RobolectricDaggerTestRunner
import org.junit.Assert
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Test for categories creation
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CategoryTests : BaseTest {

    constructor()

    @Inject lateinit var categoryInteractor: CategoryInteractor

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.injector as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory("1", "Test", 1)
        categoryInteractor.createOrUpdateCategory("delete", "Test for delete", 3)
        categoryInteractor.createOrUpdateCategory(MockCategory.instance.category3)
    }

    // Check first if categories where created successfully
    @Test()
    fun test0_categoriesCreated() {
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