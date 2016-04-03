package com.pinup.pfm.test.dao.category

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
class CategoryTestCreate : BaseTest {

    constructor()

    @Inject lateinit var categoryInteractor: CategoryInteractor

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.injector as TestComponent).inject(this)
    }

    @Test()
    fun testCreateCategory() {
        categoryInteractor.createOrUpdateCategory("1", "Test", 1)
        categoryInteractor.createOrUpdateCategory("2", "Test", 2)

        Assert.assertEquals("Non 2 items presented in the database", 2, categoryInteractor.listAllCategories().size)
    }
}