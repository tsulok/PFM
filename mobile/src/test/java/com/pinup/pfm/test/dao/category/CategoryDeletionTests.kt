package com.pinup.pfm.test.dao.category

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
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Test for categories
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CategoryDeletionTests : BaseTest() {

    @Inject lateinit var categoryInteractor: CategoryInteractor

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.applicationComponent as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory(MockCategory.instance.category)
    }

    @Test
    fun testDelete() {
        Assert.assertEquals("Non 1 items presented in the database", 1, categoryInteractor.listAllCategories().size)
        val category = categoryInteractor.getCategoryByServerId(MockCategory.instance.category.serverId)
        Assert.assertNotNull("Category is null", category)

        categoryInteractor.deleteCategory(category)

        val categoryAfter = categoryInteractor.getCategoryByServerId("delete")
        Assert.assertNull("Category is not null after deletion", categoryAfter)
    }
}