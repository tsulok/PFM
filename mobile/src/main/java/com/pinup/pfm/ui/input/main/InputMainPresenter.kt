package com.pinup.pfm.ui.input.main

import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.manager.CurrencyManager
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.ui.core.view.BasePresenter
import java.util.*
import javax.inject.Inject

/**
 * Presenter for input main
 */
class InputMainPresenter @Inject constructor(val currencyInteractor: CurrencyInteractor,
                                             val categoryInteractor: CategoryInteractor,
                                             val transactionInteractor: TransactionInteractor,
                                             val currentTransactionInteractor: CurrentTransactionInteractor)
    : BasePresenter<InputMainScreen>() {

    companion object {
        @JvmStatic val TAG = InputMainPresenter::class.java.canonicalName
    }

    init {
        // Initially load the general saved one
        currentTransactionInteractor.transactionCurrency = currencyInteractor.getSelectedCurrency()
    }

    fun loadCurrentValue() {
        screen?.updateValue(currentTransactionInteractor.formatValue())
    }

    /**
     * Load the currently selected currency
     */
    fun loadCurrentlySelectedCurrency() {
        screen?.updateSelectedCurrency(currentTransactionInteractor.transactionCurrency)
    }

    /**
     * Update the transaction's currency
     * @param currency The new currency
     */
    fun updateSelectedCurrency(currency: Currency) {
        currentTransactionInteractor.transactionCurrency = currency
        loadCurrentlySelectedCurrency()
    }

    /**
     * Show supported currencies
     */
    fun showSupportedCurrencies() {
        screen?.showSupportedCurrencies(
                currentTransactionInteractor.transactionCurrency,
                CurrencyManager.instance.getAvailableCurrencies().sortedBy { p -> p.currencyCode }
        )
    }

    /**
     * Add value to the input
     */
    fun addValue(value: Double) {
        when (currentTransactionInteractor.keyboardType) {
            KeyboardType.Normal -> {
                if (currentTransactionInteractor.transactionCurrentValueText.length != 0 || value != 0.0) {
                    currentTransactionInteractor.transactionCurrentValueText += value.toInt()
                }
            }
            else -> Logger.t(TAG).i("Non supported keyboard type")
        }
        screen?.updateValue(currentTransactionInteractor.formatValue())
    }

    /**
     * Add decimal place if none
     */
    fun addDecimalPlace() {
        if (!currentTransactionInteractor.transactionCurrentValueText.contains(".")) {
            currentTransactionInteractor.transactionCurrentValueText += "."
            screen?.updateValue(currentTransactionInteractor.formatValue())
        }
    }

    /**
     * Remove last digit
     */
    fun removeLastDigit() {
        if (currentTransactionInteractor.transactionCurrentValueText.length == 0) {
            screen?.updateValue("0")
            return
        }

        val currentValueString = currentTransactionInteractor.transactionCurrentValueText
        currentTransactionInteractor.transactionCurrentValueText = currentValueString.substring(0, currentValueString.length - 1)
        screen?.updateValue(currentTransactionInteractor.formatValue())
    }

    /**
     * Save the transaction
     */
    fun saveTransaction(transactionName: String) {
        if (currentTransactionInteractor.transactionCurrentValueText.isEmpty()) {
            screen?.showMissingTransactionArgument("Amount is missing")
            return
        }

        if (currentTransactionInteractor.transactionCurrency == null) {
            screen?.showMissingTransactionArgument("Currency is missing")
            return
        }

        if (transactionName.isEmpty()) {
            screen?.showMissingTransactionArgument("Name is missing")
            return
        }

        currentTransactionInteractor.transactionNameText = transactionName

        val transactionResultPair = currentTransactionInteractor.saveTransaction()

        if (transactionResultPair == null) {
            screen?.transactionSaveFailed()
        } else {
            screen?.transactionSaved(transactionResultPair.first, transactionResultPair.second)
        }
    }

    fun reset() {
        currentTransactionInteractor.transactionCurrentValueText = "0"
    }

    fun getSelectedKeyboardType(): KeyboardType {
        return currentTransactionInteractor.keyboardType
    }

    fun getCurrentTransactionName(): String {
        return currentTransactionInteractor.transactionNameText
    }
}