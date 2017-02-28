package com.pinup.pfm.domain.manager.currency

import java.util.*

/**
 * Manager for currencies
 */
class CurrencyManager private constructor() {
    init { /* Creates this singleton */ }

    private object Holder { val INSTANCE = CurrencyManager() }

    companion object {
        val instance: CurrencyManager by lazy { Holder.INSTANCE }
    }

    fun getAvailableCurrencies(): Set<Currency> {
        return Currency.getAvailableCurrencies()
    }
}