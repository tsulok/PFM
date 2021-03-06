package com.pinup.pfm.ui.input.main

import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.transaction.TransactionAction
import java.util.*

/**
 * Screen actions for input container
 */
interface InputMainScreen: BaseScreen {

    /**
     * Update the selected currency
     * @param currency The new currency
     */
    fun updateSelectedCurrency(currency: Currency?)

    /**
     * Show supported currencies
     * @param selectedCurrency The currently selected currency - null if none is selected
     * @param availableCurrencies The list of the available currencies
     */
    fun showSupportedCurrencies(selectedCurrency: Currency?, availableCurrencies: List<Currency>)

    /**
     * Update the value in a formatted currency
     */
    fun updateValue(value: String)

    /**
     * Shows the missing argument before the transaction could be saved
     */
    fun showMissingTransactionArgument(message: String)

    /**
     * Transaction save succeeded
     */
    fun transactionSaved(transaction: Transaction, action: TransactionAction)

    /**
     * Transaction save failed
     */
    fun transactionSaveFailed()

    /**
     * View should clear inputs to initials
     */
    fun resetView()
}