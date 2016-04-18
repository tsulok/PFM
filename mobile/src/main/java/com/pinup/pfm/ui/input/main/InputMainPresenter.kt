package com.pinup.pfm.ui.input.main

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.manager.CurrencyManager
import com.pinup.pfm.ui.core.view.BasePresenter
import com.pinup.pfm.utils.helper.SharedPreferencesHelper
import java.util.*
import javax.inject.Inject

/**
 * Presenter for input main
 */
class InputMainPresenter : BasePresenter<InputMainScreen> {

    @Inject lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    var selectedCurrency: Currency?

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)

        // Initially load the saved one
        selectedCurrency = sharedPreferencesHelper.getSelectedCurrency()
    }

    fun loadCurrentlySelectedCurrency() {
        screen?.updateSelectedCurrency(selectedCurrency)
    }

    fun updateSelectedCurrency(currency: Currency) {
        selectedCurrency = currency
        screen?.updateSelectedCurrency(currency)
    }

    fun showSupportedCurrencies() {
        screen?.showSupportedCurrencies(
                selectedCurrency,
                CurrencyManager.instance.getAvailableCurrencies().sortedBy { p -> p.currencyCode }
        )
    }
}