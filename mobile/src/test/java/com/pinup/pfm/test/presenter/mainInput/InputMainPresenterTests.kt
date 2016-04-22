package com.pinup.pfm.test.presenter.mainInput

import com.pinup.pfm.BuildConfig
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.TestComponent
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.test.utils.BaseTest
import com.pinup.pfm.test.utils.RobolectricDaggerTestRunner
import com.pinup.pfm.ui.input.main.InputMainPresenter
import junit.framework.Assert
import org.apache.maven.artifact.ant.shaded.StringUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Test for main input main presenter
 */
@RunWith(RobolectricDaggerTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class InputMainPresenterTests : BaseTest() {

    @Inject lateinit var inputMainPresenter: InputMainPresenter

    @Before
    fun setUp() {
        setTestInjector()
        (PFMApplication.injector as TestComponent).inject(this)

        // Use keyboard type as normal
        inputMainPresenter.keyboardType = KeyboardType.Normal
    }

    @Test
    fun testFirstValueAdditionZero() {
        inputMainPresenter.addValue(0.0)
        Assert.assertEquals("Currency text is not empty", 0, inputMainPresenter.currentValueString.length)
    }

    @Test
    fun testFirstValueAdditionNonZero() {
        inputMainPresenter.addValue(5.0)
        Assert.assertEquals("Currency text is not matching", "5", inputMainPresenter.currentValueString)
    }

    @Test
    fun testValueAddition() {
        inputMainPresenter.currentValueString = "456"
        Assert.assertEquals("Currency text is not matching", "456", inputMainPresenter.currentValueString)
        inputMainPresenter.addValue(5.0)
        Assert.assertEquals("Currency text is not matching", "4565", inputMainPresenter.currentValueString)
    }

    @Test
    fun testDecimalPlaceAddition() {
        inputMainPresenter.addDecimalPlace()
        Assert.assertEquals("Currency text decimal place is not the last", '.', inputMainPresenter.currentValueString.last())
    }

    @Test
    fun testDecimalPlaceMultipleAddition() {
        inputMainPresenter.currentValueString = "4.56"
        inputMainPresenter.addDecimalPlace()
        Assert.assertEquals("Multiple decimal places present", 1, StringUtils.countMatches(inputMainPresenter.currentValueString, "."))
    }

    @Test
    fun testValueRemovalEmpty() {
        inputMainPresenter.removeLastDigit()
        Assert.assertEquals("Currency text is not empty", 0, inputMainPresenter.currentValueString.length)
    }

    @Test
    fun testValueRemoval() {
        inputMainPresenter.currentValueString = "565"
        inputMainPresenter.removeLastDigit()
        Assert.assertEquals("Currency text is not matching", "56", inputMainPresenter.currentValueString)
    }

    @Test
    fun testDecimalRemoval() {
        inputMainPresenter.currentValueString = "565."
        inputMainPresenter.removeLastDigit()
        Assert.assertEquals("Decimal place present", 0, StringUtils.countMatches(inputMainPresenter.currentValueString, "."))
    }
}