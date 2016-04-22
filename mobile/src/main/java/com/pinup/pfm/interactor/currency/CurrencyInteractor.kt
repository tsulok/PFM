package com.pinup.pfm.interactor.currency

import android.content.SharedPreferences
import com.pinup.pfm.PFMApplication
import java.util.*
import javax.inject.Inject

/**
 * Interactor for handling currencies
 */
class CurrencyInteractor {

    @Inject lateinit var sharedPreferences: SharedPreferences

    constructor() {
        PFMApplication.injector.inject(this)
    }

    /**
     * Update the selected currency
     * @param currency The new currency to be saved
     */
    fun updateSelectedCurrency(currency: Currency) {
        sharedPreferences.edit().putString(PREF_KEY_CURRENT_CURRENCY, currency.currencyCode).apply()
    }

    /**
     * Returns the saved currency from the preferences
     * @return the saved currency from the preferences
     */
    fun getSelectedCurrency(): Currency? {
        val selectedCurrencyCode = sharedPreferences.getString(PREF_KEY_CURRENT_CURRENCY, "")
        if (selectedCurrencyCode.isBlank()) {
            return null
        }

        return Currency.getInstance(selectedCurrencyCode)
    }

    /**
     * Reset the saved currency
     */
    fun resetSavedCurrency() {
        sharedPreferences.edit().remove(PREF_KEY_CURRENT_CURRENCY).apply()
    }

    companion object {
        @JvmStatic val PREF_KEY_CURRENT_CURRENCY = "currentCurrency"
    }
}