package com.pinup.pfm.interactor.transaction

import com.google.android.gms.maps.model.LatLng
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.interactor.utils.StorageInteractor
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.model.transaction.TransactionAction
import com.pinup.pfm.ui.input.action.camera.InputActionCameraPresenter
import com.pinup.pfm.ui.input.main.InputMainPresenter
import java.io.File
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

/**
 * Current transaction interactor
 */
class CurrentTransactionInteractor @Inject constructor(val transactionInteractor: TransactionInteractor,
                                                       val currencyInteractor: CurrencyInteractor,
                                                       val storageInteractor: StorageInteractor) {

    var keyboardType: KeyboardType = KeyboardType.Normal

    var transactionNameText: String = ""
    var transactionCurrentValueText: String = ""
    var transactionImageFile: File? = null
    var transactionDate: Date = Date()
    var transactionDescription: String = ""
    var transactionSelectedCategory: Category? = null
    var transactionLocation: LatLng? = null
    var transactionCurrency: Currency? = null

    var savedTransaction: Transaction? = null

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
        transactionNameText = transaction.name

        val numberFormat = currencyInteractor.getCurrencyNumberFormat(transaction.currency)
        // Remove all non breaking spaces & change commas when formatting the value
        transactionCurrentValueText = numberFormat.format(transaction.amount).replace("\u00A0", "").replace(",", ".")

        if (transaction.imageUri != null) {
            transactionImageFile = File(transaction.imageUri)
        }
        transactionDate = transaction.date
        transactionDescription = transaction.description

        if (transaction.latitude != null && transaction.longitude != null) {
            transactionLocation = LatLng(transaction.latitude, transaction.longitude)
        }
    }

    /**
     * Saves the transaction
     */
    fun saveTransaction(): Pair<Transaction, TransactionAction>? {
        // Transaction value is mandatory
        if (transactionCurrentValueText.isEmpty()) {
            return null
        }

        // Transaction currency is mandatory
        if (transactionCurrency == null) {
            return null
        }

        val amount = transactionCurrentValueText.toDouble()

        // First load the saved transaction if any
        var transactionAction: TransactionAction = TransactionAction.MODIFIED
        var transaction = savedTransaction

        // If it is a new, then create it
        if (transaction == null) {
            transactionAction = TransactionAction.NEW
            transaction = transactionInteractor.createTransaction(transactionNameText,
                    amount, transactionCurrency!!.currencyCode, transactionSelectedCategory)
        } else {
            transactionInteractor.updateTransactionName(transaction, transactionNameText)
            transactionInteractor.updateTransactionAmount(transaction, amount, transactionCurrency)
            transactionInteractor.updateTransactionCategory(transaction, transactionSelectedCategory)
        }

        transactionInteractor.updateTransactionDate(transaction, transactionDate)
        transactionInteractor.updateTransactionDescription(transaction, transactionDescription)
        saveFinalImageToTransaction(transaction)

        if (transactionLocation != null) {
            transactionInteractor.updateTransactionLocation(transaction, transactionLocation!!)
        }

        resetTransaction()

        return Pair(transaction, transactionAction)
    }

    private fun saveFinalImageToTransaction(transaction: Transaction) {

        // Do nothing if there is no image
        if (transactionImageFile == null) {
            return
        }

        val imageName = "${transaction.id}.jpg"
        val persistentFile = storageInteractor.moveFile(transactionImageFile!!, imageName)
        transactionInteractor.updateTransactionImageUri(transaction, persistentFile?.canonicalPath)
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