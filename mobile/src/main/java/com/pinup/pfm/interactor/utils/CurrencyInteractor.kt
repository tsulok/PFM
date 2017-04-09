package com.pinup.pfm.interactor.utils

import android.content.SharedPreferences
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.domain.manager.preferences.SharedPreferencesManager
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

interface ICurrencyInteractor {
    /**
     * Update the selected currency
     * @param currency The new currency to be saved
     */
    fun updateSelectedCurrency(currency: Currency)

    /**
     * Returns the saved currency from the preferences
     * @return the saved currency from the preferences
     */
    fun getSelectedCurrency(): Currency?

    /**
     * Reset the saved currency
     */
    fun resetSavedCurrency()

    /**
     * Returns the number format for the selected currency
     * @return the number format for the selected currency
     */
    fun getCurrencyNumberFormat(currency: String): NumberFormat

    /**
     * Returns the symbol of the desired currency
     * @return the symbol of the desired currency
     */
    fun getCurrencySymbol(currency: String): String
}

/**
 * Interactor for handling currencies
 */
class CurrencyInteractor
@Inject constructor(private val preferencesManager: SharedPreferencesManager) : ICurrencyInteractor {

    /**
     * Update the selected currency
     * @param currency The new currency to be saved
     */
    override fun updateSelectedCurrency(currency: Currency) {
        preferencesManager.setStringPreference(PREF_KEY_CURRENT_CURRENCY, currency.currencyCode)
    }

    /**
     * Returns the saved currency from the preferences
     * @return the saved currency from the preferences
     */
    override fun getSelectedCurrency(): Currency? {
        val selectedCurrencyCode = preferencesManager.getStringPreference(PREF_KEY_CURRENT_CURRENCY) ?: return null
        return Currency.getInstance(selectedCurrencyCode)
    }

    /**
     * Reset the saved currency
     */
    override fun resetSavedCurrency() {
        preferencesManager.clearPreference(PREF_KEY_CURRENT_CURRENCY)
    }

    /**
     * Returns the number format for the selected currency
     * @return the number format for the selected currency
     */
    override fun getCurrencyNumberFormat(currency: String): NumberFormat {
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
    override fun getCurrencySymbol(currency: String): String {
        return Currency.getInstance(currency).symbol
    }

    companion object {
        @JvmStatic val PREF_KEY_CURRENT_CURRENCY = "currentCurrency"
    }
}