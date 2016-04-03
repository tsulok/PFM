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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TransactionTests : BaseTest {

    constructor()

    @Inject lateinit var transactionInteractor: TransactionInteractor
    @Inject lateinit var categoryInteractor: CategoryInteractor

    lateinit var transaction1: Transaction
    lateinit var transaction2: Transaction
    lateinit var transactionToDelete: Transaction

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.injector as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory(MockCategory.instance.category)

        transaction1 = transactionInteractor.createTransaction("Transaction 1", 100.0, "HUF", MockCategory.instance.category)
        transaction2 = transactionInteractor.createTransaction("Transaction 2", 200.0, "HUF", MockCategory.instance.category)
        transactionToDelete = transactionInteractor.createTransaction("Transaction For delete", 300.0, "USD", MockCategory.instance.category)
    }

    // Check first if transactions where created successfully
    @Test()
    fun test0_createTransactions() {
        Assert.assertEquals("Non 3 items presented in the database", 3, transactionInteractor.listAllTransaction().size)
        Assert.assertNotNull("Transaction 1 is null", transaction1)
        Assert.assertNotNull("Transaction 2 is null", transaction2)
        Assert.assertNotNull("Transaction Delete is null", transactionToDelete)
    }

    @Test
    fun testServerIdAssociate() {
        transactionInteractor.updateTransactionServerId(transaction1, "serverId")
        Assert.assertNotNull("Transaction sync date is not updated", transaction1.lastSyncDate)

        val transaction = transactionInteractor.getTransactionByServerId("serverId")
        Assert.assertNotNull("Transaction is null", transaction)
    }

    @Test
    fun testUpdateLocation() {
        val latLng = LatLng(100.0, 200.0)
        transactionInteractor.updateTransactionLocation(transaction1, latLng)

        Assert.assertEquals("Latitude miss match", latLng.latitude, transaction1.latitude, 1.0)
        Assert.assertEquals("Longitude miss match", latLng.longitude, transaction1.longitude, 1.0)
    }

    @Test
    fun testUpdateAmount() {
        transactionInteractor.updateTransactionAmount(transaction1, 500.0, "USD")

        Assert.assertEquals("Amount miss match", 500.0, transaction1.amount, 1.0)
        Assert.assertEquals("Currency miss match", "USD", transaction1.currency)
    }

    @Test
    fun testUpdateDate() {
        val date = Date(887979)
        transactionInteractor.updateTransactionDate(transaction1, date)

        Assert.assertEquals("Date miss match", date.time, transaction1.date.time)
    }

    @Test
    fun testUpdateDescription() {
        val description = "Test description"
        transactionInteractor.updateTransactionDescription(transaction1, description)

        Assert.assertEquals("Description miss match", description, transaction1.description)
    }

    @Test
    fun testUpdateName() {
        val name = "Test name"
        transactionInteractor.updateTransactionName(transaction1, name)

        Assert.assertEquals("Name miss match", name, transaction1.name)
    }

    @Test
    fun testUpdateSyncTime() {
        val currentDate = Date()
        transactionInteractor.updateTransactionSynced(transaction1)

        Assert.assertTrue("Sync date not updated", transaction1.lastSyncDate.time >= currentDate.time)
    }

    @Test
    fun testUpdateImageUri_1() {
        val imageUri = "http://fileName.jpg"
        val currentDate = Date()
        transactionInteractor.updateTransactionImageUri(transaction1, imageUri)

        Assert.assertEquals("Image uri not updated", transaction1.imageUri, imageUri)
        Assert.assertTrue("ImageModify date not updated", transaction1.lastImageModifyDate.time >= currentDate.time)
    }

    // Server update
    @Test
    fun testUpdateImageUri_2() {
        val imageUri = "http://fileName2.jpg"
        val currentDate = Date()
        transactionInteractor.updateTransactionImageUri(transaction1, imageUri, true)

        Assert.assertEquals("Image uri not updated", transaction1.imageUri, imageUri)
        Assert.assertTrue("ImageModify date not updated", transaction1.lastImageModifyDate.time < currentDate.time)
        Assert.assertTrue("ImageSync date not updated", transaction1.lastImageSyncDate.time >= currentDate.time)
    }

    @Test
    fun testDelete() {
        Assert.assertNotNull("Transaction is null", transactionToDelete)

        transactionInteractor.deleteTransaction(transactionToDelete)

        val transactionAfterDelete = transactionInteractor.getTransactionById(transactionToDelete.id)
        Assert.assertNull("Transaction is not null after deletion", transactionAfterDelete)
    }
}