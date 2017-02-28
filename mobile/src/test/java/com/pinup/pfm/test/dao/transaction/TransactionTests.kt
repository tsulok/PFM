package com.pinup.pfm.test.dao.transaction

import com.google.android.gms.maps.model.LatLng
import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.test.mock.MockCategory
import com.pinup.pfm.test.utils.BaseTest
import com.pinup.pfm.test.utils.RobolectricDaggerTestRunner
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*
import javax.inject.Inject

/**
 * Test for transactions
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class TransactionTests : BaseTest() {

    @Inject lateinit var transactionInteractor: ITransactionInteractor
    @Inject lateinit var categoryInteractor: ICategoryInteractor

    lateinit var transaction: Transaction

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.applicationComponent as TestComponent).inject(this)

        categoryInteractor.createOrUpdateCategory(MockCategory.instance.category)

        transaction = transactionInteractor.createTransaction("Transaction 1", 100.0, "HUF", MockCategory.instance.category)
    }

    @Test
    fun testSetupCompleted() {
        Assert.assertNotNull("Transaction 1 is null", transaction)
    }

    @Test
    fun testServerIdAssociate() {
        transactionInteractor.updateTransactionServerId(transaction, "serverId")
        Assert.assertNotNull("Transaction sync date is not updated", transaction.lastSyncDate)

        val transaction = transactionInteractor.getTransactionByServerId("serverId")
        Assert.assertNotNull("Transaction is null", transaction)
    }

    @Test
    fun testUpdateLocation() {
        val latLng = LatLng(100.0, 200.0)
        transactionInteractor.updateTransactionLocation(transaction, latLng)

        Assert.assertEquals("Latitude miss match", latLng.latitude, transaction.latitude, 1.0)
        Assert.assertEquals("Longitude miss match", latLng.longitude, transaction.longitude, 1.0)
    }

    @Test
    fun testUpdateAmount() {
        transactionInteractor.updateTransactionAmount(transaction, 500.0, Currency.getInstance(Locale.US))

        Assert.assertEquals("Amount miss match", 500.0, transaction.amount, 1.0)
        Assert.assertEquals("Currency miss match", Currency.getInstance(Locale.US).currencyCode, transaction.currency)
    }

    @Test
    fun testUpdateDate() {
        val date = Date(887979)
        transactionInteractor.updateTransactionDate(transaction, date)

        Assert.assertEquals("Date miss match", date.time, transaction.date.time)
    }

    @Test
    fun testUpdateDescription() {
        val description = "Test description"
        transactionInteractor.updateTransactionDescription(transaction, description)

        Assert.assertEquals("Description miss match", description, transaction.description)
    }

    @Test
    fun testUpdateName() {
        val name = "Test name"
        transactionInteractor.updateTransactionName(transaction, name)

        Assert.assertEquals("Name miss match", name, transaction.name)
    }

    @Test
    fun testUpdateSyncTime() {
        val currentDate = Date()
        transactionInteractor.updateTransactionSynced(transaction)

        Assert.assertTrue("Sync date not updated", transaction.lastSyncDate.time >= currentDate.time)
    }

    @Test
    fun testUpdateImageUri_1() {
        val imageUri = "http://fileName.jpg"
        val currentDate = Date()
        transactionInteractor.updateTransactionImageUri(transaction, imageUri)

        Assert.assertEquals("Image uri not updated", transaction.imageUri, imageUri)
        Assert.assertTrue("ImageModify date not updated", transaction.lastImageModifyDate.time >= currentDate.time)
    }

    // Server update
    @Test
    fun testUpdateImageUri_2() {
        val imageUri = "http://fileName2.jpg"
        val currentDate = Date()
        transactionInteractor.updateTransactionImageUri(transaction, imageUri, true)

        Assert.assertEquals("Image uri not updated", transaction.imageUri, imageUri)
        Assert.assertTrue("ImageModify date not updated", transaction.lastImageModifyDate.time < currentDate.time)
        Assert.assertTrue("ImageSync date not updated", transaction.lastImageSyncDate.time >= currentDate.time)
    }
}