package com.pinup.pfm.ui.input.main

import com.pinup.pfm.ui.core.view.BaseScreen
import java.util.*

/**
 * Screen actions for input container
 */
interface InputMainScreen : BaseScreen {

    fun updateSelectedCurrency(currency: Currency?)
    fun showSupportedCurrencies(selectedCurrency: Currency?, availableCurrencies: List<Currency>)
}