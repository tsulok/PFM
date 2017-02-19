package com.pinup.pfm.interactor.utils

import android.content.SharedPreferences
import com.pinup.pfm.PFMApplication
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * Interactor for handling currencies
 */
class CurrencyInteractor @Inject constructor(val sharedPreferences: SharedPreferences) {

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

    /**
     * Returns the number format for the selected currency
     * @return the number format for the selected currency
     */
    fun getCurrencyNumberFormat(currency: String): NumberFormat {
        val currency = Currency.getInstance(currency)

        val numberFormat = NumberFormat.getInstance()
        numberFormat.minimumFractionDigits = currency.defaultFractionDigits
        numberFormat.maximumFractionDigits = currency.defaultFractionDigits

        return numberFormat
    }

    /**
     * Returns the symbol of the desired currency
     * @return the symbol of the desired currency
     */
    fun getCurrencySymbol(currency: String): String {
        return Currency.getInstance(currency).symbol
    }

    companion object {
        @JvmStatic val PREF_KEY_CURRENT_CURRENCY = "currentCurrency"
    }
}