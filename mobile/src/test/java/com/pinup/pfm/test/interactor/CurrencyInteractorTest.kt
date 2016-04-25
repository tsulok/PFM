package com.pinup.pfm.test.interactor

import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.interactor.utils.CurrencyInteractor
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
 * Test for currency interactor
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CurrencyInteractorTest : BaseTest() {

    @Inject lateinit var currencyInteractor: CurrencyInteractor

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.injector as TestComponent).inject(this)
        // Initially the shared preferences is clear for currency
        currencyInteractor.resetSavedCurrency()
    }

    @Test
    fun testCurrencyEmptyByDefault() {
        val savedCurrency = currencyInteractor.getSelectedCurrency()
        Assert.assertNull("Currency is not null by default", savedCurrency)
    }

    @Test
    fun testCurrencyCreation() {
        currencyInteractor.updateSelectedCurrency(Currency.getInstance(Locale.FRANCE))

        val savedCurrency = currencyInteractor.getSelectedCurrency()
        Assert.assertNotNull("Currency is null after save", savedCurrency)
    }

    @Test
    fun testCurrencyCreationMatch() {
        currencyInteractor.updateSelectedCurrency(Currency.getInstance(Locale.FRANCE))

        val savedCurrency = currencyInteractor.getSelectedCurrency()
        Assert.assertNotNull("Currency is null after save", savedCurrency)

        Assert.assertEquals("Currency codes not matching", savedCurrency?.currencyCode, Currency.getInstance(Locale.FRANCE).currencyCode)
    }
}