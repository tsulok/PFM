package com.pinup.pfm.interactor.transaction

import com.google.android.gms.maps.model.LatLng
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.ui.input.main.InputMainPresenter
import java.io.File
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * Current transaction interactor
 */
class CurrentTransactionInteractor {

    @Inject lateinit var transactionInteractor: TransactionInteractor

    var keyboardType: KeyboardType = KeyboardType.Normal

    var transactionCurrentValueText: String = ""
    var transactionImageFile: File? = null
    var transactionDate: Date = Date()
    var transactionDescription: String = ""
    var transactionSelectedCategory: Category? = null
    var transactionLocation: LatLng? = null
    var transactionCurrency: Currency? = null

    var savedTransaction: Transaction? = null

    constructor() {
        PFMApplication.injector.inject(this)
    }

    /**
     * Resets the transaction
     */
    fun resetTransaction() {
        transactionCurrentValueText = ""
        transactionImageFile = null
        transactionDate = Date()
        transactionDescription = ""
        transactionSelectedCategory = null
        transactionLocation = null
        transactionCurrency = null
        savedTransaction = null
    }

    fun loadSavedTransaction(transaction: Transaction) {
        savedTransaction = transaction
        transactionCurrency = Currency.getInstance(transaction.currency)

        val numberFormat = NumberFormat.getInstance()
        numberFormat.minimumFractionDigits = transactionCurrency?.defaultFractionDigits ?: 0
        numberFormat.maximumFractionDigits = transactionCurrency?.defaultFractionDigits ?: 0

        transactionCurrentValueText = numberFormat.format(transaction.amount)
    }

    /**
     * Saves the transaction
     */
    fun saveTransaction(): Boolean {
        // Transaction value is mandatory
        if (transactionCurrentValueText.isEmpty()) {
            return false
        }

        // Transaction currency is mandatory
        if (transactionCurrency == null) {
            return false
        }

        val amount = transactionCurrentValueText.toDouble()

        // First load the saved transaction if any
        var transaction = savedTransaction

        // If it is a new, then create it
        if (transaction == null) {
            transaction = transactionInteractor.createTransaction("",
                    amount, transactionCurrency!!.currencyCode, transactionSelectedCategory)
        } else {
            transactionInteractor.updateTransactionAmount(transaction, amount, transactionCurrency)
            transactionInteractor.updateTransactionCategory(transaction, transactionSelectedCategory)
        }

        transactionInteractor.updateTransactionDate(transaction, transactionDate)
        transactionInteractor.updateTransactionDescription(transaction, transactionDescription)
        transactionInteractor.updateTransactionImageUri(transaction, transactionImageFile?.absolutePath)

        if (transactionLocation != null) {
            transactionInteractor.updateTransactionLocation(transaction, transactionLocation!!)
        }

        resetTransaction()

        return true
    }

    /**
     * Format amount
     * @return The formatted amount
     */
    fun formatValue(): String {
        when (keyboardType) {
            KeyboardType.Normal -> {
                if (transactionCurrentValueText.length == 0) {
                    return "0"
                }
                return transactionCurrentValueText
            }
            else -> Logger.t(InputMainPresenter.TAG).i("Non supported keyboard type")
        }
        return transactionCurrentValueText.toString()
    }
}