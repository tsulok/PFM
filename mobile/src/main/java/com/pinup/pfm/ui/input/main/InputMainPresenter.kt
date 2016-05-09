package com.pinup.pfm.ui.input.main

import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.manager.CurrencyManager
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.ui.core.view.BasePresenter
import java.util.*
import javax.inject.Inject

/**
 * Presenter for input main
 */
class InputMainPresenter : BasePresenter<InputMainScreen> {

    companion object {
        @JvmStatic val TAG = InputMainPresenter::class.java.canonicalName
    }

    @Inject lateinit var currencyInteractor: CurrencyInteractor
    @Inject lateinit var categoryInteractor: CategoryInteractor
    @Inject lateinit var transactionInteractor: TransactionInteractor
    @Inject lateinit var currentTransactionInteractor: CurrentTransactionInteractor

    var selectedCurrency: Currency?
    var keyboardType: KeyboardType = KeyboardType.Normal
    var currentValue: Double = 0.0
    var currentValueString: String = ""

    constructor() : super() {
        PFMApplication.injector.inject(this)

        // Initially load the general saved one
        selectedCurrency = currencyInteractor.getSelectedCurrency()
    }

    fun loadCurrentValue() {
        screen?.updateValue(currentTransactionInteractor.formatValue())
    }

    /**
     * Load the currently selected currency
     */
    fun loadCurrentlySelectedCurrency() {
        screen?.updateSelectedCurrency(selectedCurrency)
    }

    /**
     * Update the transaction's currency
     * @param currency The new currency
     */
    fun updateSelectedCurrency(currency: Currency) {
        selectedCurrency = currency
        currentTransactionInteractor.transactionCurrency = selectedCurrency
        loadCurrentlySelectedCurrency()
    }

    /**
     * Show supported currencies
     */
    fun showSupportedCurrencies() {
        screen?.showSupportedCurrencies(
                selectedCurrency,
                CurrencyManager.instance.getAvailableCurrencies().sortedBy { p -> p.currencyCode }
        )
    }

    /**
     * Add value to the input
     */
    fun addValue(value: Double) {
        when (keyboardType) {
            KeyboardType.Normal -> {
                if (currentValueString.length != 0 || value != 0.0) {
                    currentValueString += value.toInt()
                }
            }
            else -> Logger.t(TAG).i("Non supported keyboard type")
        }
        currentTransactionInteractor.transactionCurrentValueText = currentValueString
        screen?.updateValue(currentTransactionInteractor.formatValue())
    }

    /**
     * Add decimal place if none
     */
    fun addDecimalPlace() {
        if (!currentValueString.contains(".")) {
            currentValueString += "."
            currentTransactionInteractor.transactionCurrentValueText = currentValueString
            screen?.updateValue(currentTransactionInteractor.formatValue())
        }
    }

    /**
     * Remove last digit
     */
    fun removeLastDigit() {
        if (currentValueString.length == 0) {
            screen?.updateValue("0")
            return
        }

        currentValueString = currentValueString.substring(0, currentValueString.length - 1)
        currentTransactionInteractor.transactionCurrentValueText = currentValueString
        screen?.updateValue(currentTransactionInteractor.formatValue())
    }
}