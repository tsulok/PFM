package com.pinup.pfm.test.dao.category

import android.R
import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.test.utils.BaseTest
import com.pinup.pfm.test.utils.RobolectricDaggerTestRunner
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Test for categories creation
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CategoryTestRead : BaseTest {

    constructor()

    @Inject lateinit var categoryInteractor: CategoryInteractor

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.injector as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory("1", "Test", 1)
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
}