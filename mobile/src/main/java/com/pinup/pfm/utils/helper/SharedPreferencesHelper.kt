package com.pinup.pfm.utils.helper

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * Helper for shared preferences
 */
class SharedPreferencesHelper {

    private var sharedPreferences: SharedPreferences

    constructor(context: Context) {
        sharedPreferences = context.getSharedPreferences("PFMSharedPref", Context.MODE_PRIVATE)
    }

    fun updateSelectedCurrency(currency: Currency) {
        sharedPreferences.edit().putString(SharedPrefKey.CurrentCurrency.identifier, currency.currencyCode).apply()
    }

    fun getSelectedCurrency(): Currency? {
        val selectedCurrencyCode = sharedPreferences.getString(SharedPrefKey.CurrentCurrency.identifier, "")
        if (selectedCurrencyCode.isBlank()) {
            return null
        }

        return Currency.getInstance(selectedCurrencyCode)
    }

    enum class SharedPrefKey(val identifier: String) {
        CurrentCurrency("CurrentCurrency")
    }
}