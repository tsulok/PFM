package com.pinup.pfm.test.dao.transaction

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.model.database.Transaction
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
import java.util.*
import javax.inject.Inject

/**
 * Test for transactions
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class TransactionDeletionTest : BaseTest {

    constructor()

    @Inject lateinit var transactionInteractor: TransactionInteractor
    @Inject lateinit var categoryInteractor: ICategoryInteractor

    lateinit var transactionToDelete: Transaction

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.applicationComponent as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory(MockCategory.instance.category)
        transactionToDelete = transactionInteractor.createTransaction("Transaction For delete", 300.0, "USD", MockCategory.instance.category)
    }

    @Test()
    fun testDeletion() {
        Assert.assertNotNull("Transaction is null", transactionToDelete)

        transactionInteractor.deleteTransaction(transactionToDelete)

        val transactionAfterDelete = transactionInteractor.getTransactionById(transactionToDelete.id)
        Assert.assertNull("Transaction is not null after deletion", transactionAfterDelete)
    }
}