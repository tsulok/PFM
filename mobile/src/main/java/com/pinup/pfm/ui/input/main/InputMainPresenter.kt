package com.pinup.pfm.ui.input.main

import com.orhanobut.logger.Logger
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.interactor.utils.ICurrencyInteractor
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.common.ui.core.BasePresenter
import java.util.*
import javax.inject.Inject

/**
 * Presenter for input main
 */
class InputMainPresenter
@Inject constructor(private val currencyInteractor: ICurrencyInteractor,
                    private val categoryInteractor: ICategoryInteractor,
                    private val transactionInteractor: ITransactionInteractor,
                    private val transactionManager: ITransactionManager)
    : BasePresenter<InputMainScreen>() {

    companion object {
        @JvmStatic val TAG = InputMainPresenter::class.java.canonicalName
    }

    init {
        // Initially load the general saved one
        transactionManager.transactionCurrency = currencyInteractor.getSelectedCurrency()
    }

    fun loadCurrentValue() {
        screen?.updateValue(transactionManager.formatValue())
    }

    /**
     * Load the currently selected currency
     */
    fun loadCurrentlySelectedCurrency() {
        screen?.updateSelectedCurrency(transactionManager.transactionCurrency)
    }

    /**
     * Update the transaction's currency
     * @param currency The new currency
     */
    fun updateSelectedCurrency(currency: Currency) {
        transactionManager.transactionCurrency = currency
        loadCurrentlySelectedCurrency()
    }

    /**
     * Show supported currencies
     */
    fun showSupportedCurrencies() {
        screen?.showSupportedCurrencies(
                transactionManager.transactionCurrency,
                currencyInteractor.listAvailableCurrencies()
        )
    }

    /**
     * Add value to the input
     */
    fun addValue(value: Double) {
        when (transactionManager.keyboardType) {
            KeyboardType.Normal -> {
                if (transactionManager.transactionCurrentValueText.length != 0 || value != 0.0) {
                    transactionManager.transactionCurrentValueText += value.toInt()
                }
            }
            else -> Logger.t(TAG).i("Non supported keyboard type")
        }
        screen?.updateValue(transactionManager.formatValue())
    }

    /**
     * Add decimal place if none
     */
    fun addDecimalPlace() {
        if (!transactionManager.transactionCurrentValueText.contains(".")) {
            transactionManager.transactionCurrentValueText += "."
            screen?.updateValue(transactionManager.formatValue())
        }
    }

    /**
     * Remove last digit
     */
    fun removeLastDigit() {
        if (transactionManager.transactionCurrentValueText.isEmpty()) {
            screen?.updateValue("0")
            return
        }

        val currentValueString = transactionManager.transactionCurrentValueText
        transactionManager.transactionCurrentValueText = currentValueString.substring(0, currentValueString.length - 1)
        screen?.updateValue(transactionManager.formatValue())
    }

    /**
     * Save the transaction
     */
    fun saveTransaction(transactionName: String) {
        if (transactionManager.transactionCurrentValueText.isEmpty()) {
            screen?.showMissingTransactionArgument("Amount is missing")
            return
        }

        if (transactionManager.transactionCurrency == null) {
            screen?.showMissingTransactionArgument("Currency is missing")
            return
        }

        if (transactionName.isEmpty()) {
            screen?.showMissingTransactionArgument("Name is missing")
            return
        }

        transactionManager.transactionNameText = transactionName

        val transactionResultPair = transactionManager.saveTransaction()

        if (transactionResultPair == null) {
            screen?.transactionSaveFailed()
        } else {
            screen?.transactionSaved(transactionResultPair.first, transactionResultPair.second)
        }
    }

    fun reset() {
        transactionManager.resetTransaction()
        screen?.resetView()
    }

    fun getSelectedKeyboardType(): KeyboardType {
        return transactionManager.keyboardType
    }

    fun getCurrentTransactionName(): String {
        return transactionManager.transactionNameText
    }

    fun defaultCurrencyChanged(currency: Currency) {

    }
}