package com.pinup.pfm.test.dao.transaction

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.interactor.category.CategoryInteractor
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
class TransactionTests : BaseTest {

    constructor()

    @Inject lateinit var transactionInteractor: TransactionInteractor
    @Inject lateinit var categoryInteractor: CategoryInteractor

    lateinit var transaction: Transaction

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.injector as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory(MockCategory.instance.category)

        transaction = transactionInteractor.createTransaction("Transaction 1", 100.0, "HUF", MockCategory.instance.category)
    }

    @Test
    fun testServerIdAssociate() {

        Assert.assertNotNull("Transaction 1 is null", transaction)

        transactionInteractor.updateTransactionServerId(transaction, "serverId")
        Assert.assertNotNull("Transaction sync date is not updated", transaction.lastSyncDate)

        val transaction = transactionInteractor.getTransactionByServerId("serverId")
        Assert.assertNotNull("Transaction is null", transaction)
    }

    @Test
    fun testUpdateLocation() {

        Assert.assertNotNull("Transaction 1 is null", transaction)

        val latLng = LatLng(100.0, 200.0)
        transactionInteractor.updateTransactionLocation(transaction, latLng)

        Assert.assertEquals("Latitude miss match", latLng.latitude, transaction.latitude, 1.0)
        Assert.assertEquals("Longitude miss match", latLng.longitude, transaction.longitude, 1.0)
    }

    @Test
    fun testUpdateAmount() {
        Assert.assertNotNull("Transaction 1 is null", transaction)

        transactionInteractor.updateTransactionAmount(transaction, 500.0, "USD")

        Assert.assertEquals("Amount miss match", 500.0, transaction.amount, 1.0)
        Assert.assertEquals("Currency miss match", "USD", transaction.currency)
    }

    @Test
    fun testUpdateDate() {
        Assert.assertNotNull("Transaction 1 is null", transaction)

        val date = Date(887979)
        transactionInteractor.updateTransactionDate(transaction, date)

        Assert.assertEquals("Date miss match", date.time, transaction.date.time)
    }

    @Test
    fun testUpdateDescription() {

        Assert.assertNotNull("Transaction 1 is null", transaction)

        val description = "Test description"
        transactionInteractor.updateTransactionDescription(transaction, description)

        Assert.assertEquals("Description miss match", description, transaction.description)
    }

    @Test
    fun testUpdateName() {

        Assert.assertNotNull("Transaction 1 is null", transaction)

        val name = "Test name"
        transactionInteractor.updateTransactionName(transaction, name)

        Assert.assertEquals("Name miss match", name, transaction.name)
    }

    @Test
    fun testUpdateSyncTime() {

        Assert.assertNotNull("Transaction 1 is null", transaction)

        val currentDate = Date()
        transactionInteractor.updateTransactionSynced(transaction)

        Assert.assertTrue("Sync date not updated", transaction.lastSyncDate.time >= currentDate.time)
    }

    @Test
    fun testUpdateImageUri_1() {

        Assert.assertNotNull("Transaction 1 is null", transaction)

        val imageUri = "http://fileName.jpg"
        val currentDate = Date()
        transactionInteractor.updateTransactionImageUri(transaction, imageUri)

        Assert.assertEquals("Image uri not updated", transaction.imageUri, imageUri)
        Assert.assertTrue("ImageModify date not updated", transaction.lastImageModifyDate.time >= currentDate.time)
    }

    // Server update
    @Test
    fun testUpdateImageUri_2() {

        Assert.assertNotNull("Transaction 1 is null", transaction)

        val imageUri = "http://fileName2.jpg"
        val currentDate = Date()
        transactionInteractor.updateTransactionImageUri(transaction, imageUri, true)

        Assert.assertEquals("Image uri not updated", transaction.imageUri, imageUri)
        Assert.assertTrue("ImageModify date not updated", transaction.lastImageModifyDate.time < currentDate.time)
        Assert.assertTrue("ImageSync date not updated", transaction.lastImageSyncDate.time >= currentDate.time)
    }
}