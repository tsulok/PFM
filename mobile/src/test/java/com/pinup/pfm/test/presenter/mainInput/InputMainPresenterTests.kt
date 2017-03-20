package com.pinup.pfm.test.presenter.mainInput

import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.domain.manager.transaction.TransactionManager
import com.pinup.pfm.test.utils.BaseTest
import com.pinup.pfm.test.utils.RobolectricDaggerTestRunner
import com.pinup.pfm.ui.input.main.InputMainPresenter
import org.apache.maven.artifact.ant.shaded.StringUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Test for main input main presenter
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class InputMainPresenterTests : BaseTest() {

    @Inject lateinit var inputMainPresenter: InputMainPresenter
    @Inject lateinit var transactionManager: TransactionManager

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.applicationComponent as TestComponent).inject(this)
    }

    @Test
    fun testFirstValueAdditionZero() {
        inputMainPresenter.addValue(0.0)
        Assert.assertEquals("Currency text is not empty", 0, transactionManager.transactionCurrentValueText.length)
    }

    @Test
    fun testFirstValueAdditionNonZero() {
        inputMainPresenter.addValue(5.0)
        Assert.assertEquals("Currency text is not matching", "5", transactionManager.transactionCurrentValueText)
    }

    @Test
    fun testValueAddition() {
        transactionManager.transactionCurrentValueText = "456"
        Assert.assertEquals("Currency text is not matching", "456", transactionManager.transactionCurrentValueText)
        inputMainPresenter.addValue(5.0)
        Assert.assertEquals("Currency text is not matching", "4565", transactionManager.transactionCurrentValueText)
    }

    @Test
    fun testDecimalPlaceAddition() {
        inputMainPresenter.addDecimalPlace()
        Assert.assertEquals("Currency text decimal place is not the last", '.', transactionManager.transactionCurrentValueText.last())
    }

    @Test
    fun testDecimalPlaceMultipleAddition() {
        transactionManager.transactionCurrentValueText = "4.56"
        inputMainPresenter.addDecimalPlace()
        Assert.assertEquals("Multiple decimal places present", 1, StringUtils.countMatches(transactionManager.transactionCurrentValueText, "."))
    }

    @Test
    fun testValueRemovalEmpty() {
        inputMainPresenter.removeLastDigit()
        Assert.assertEquals("Currency text is not empty", 0, transactionManager.transactionCurrentValueText.length)
    }

    @Test
    fun testValueRemoval() {
        transactionManager.transactionCurrentValueText = "565"
        inputMainPresenter.removeLastDigit()
        Assert.assertEquals("Currency text is not matching", "56", transactionManager.transactionCurrentValueText)
    }

    @Test
    fun testDecimalRemoval() {
        transactionManager.transactionCurrentValueText = "565."
        inputMainPresenter.removeLastDigit()
        Assert.assertEquals("Decimal place present", 0, StringUtils.countMatches(transactionManager.transactionCurrentValueText, "."))
    }
}