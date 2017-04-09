package com.pinup.pfm.domain.manager.transaction

import com.google.android.gms.maps.model.LatLng
import com.orhanobut.logger.Logger
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.interactor.utils.ICurrencyInteractor
import com.pinup.pfm.interactor.utils.IStorageInteractor
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.model.transaction.TransactionAction
import com.pinup.pfm.ui.input.main.InputMainPresenter
import java.io.File
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

interface ITransactionManager {
    /**
     * Resets the transaction
     */
    fun resetTransaction()

    fun loadSavedTransaction(transaction: Transaction)
    /**
     * Saves the transaction
     */
    fun saveTransaction(): Pair<Transaction, TransactionAction>?

    /**
     * Format amount
     * @return The formatted amount
     */
    fun formatValue(): String

    var transactionNameText: String
    var transactionCurrentValueText: String
    var transactionImageFile: File?
    var transactionDate: Date
    var transactionDescription: String
    var transactionSelectedCategory: Category?
    var transactionLocation: LatLng?
    var transactionCurrency: Currency?
    var savedTransaction: Transaction?
    var keyboardType: KeyboardType
}

/**
 * Current transaction interactor
 */
@Singleton
class TransactionManager @Inject constructor(val transactionInteractor: TransactionInteractor,
                                             val currencyInteractor: ICurrencyInteractor,
                                             val storageInteractor: IStorageInteractor) : ITransactionManager {

    override var keyboardType: KeyboardType = KeyboardType.Normal
    override var transactionNameText: String = ""
    override var transactionCurrentValueText: String = ""
    override var transactionImageFile: File? = null
    override var transactionDate: Date = Date()
    override var transactionDescription: String = ""
    override var transactionSelectedCategory: Category? = null
    override var transactionLocation: LatLng? = null
    override var transactionCurrency: Currency? = currencyInteractor.getSelectedCurrency()
    override var savedTransaction: Transaction? = null

    /**
     * Resets the transaction
     */
    override fun resetTransaction() {
        transactionImageFile = null
        transactionDate = Date()
        transactionDescription = ""
        transactionSelectedCategory = null
        transactionLocation = null
        transactionCurrency = currencyInteractor.getSelectedCurrency()
        savedTransaction = null
        transactionCurrentValueText = ""
    }

    override fun loadSavedTransaction(transaction: Transaction) {
        savedTransaction = transaction
        transactionCurrency = Currency.getInstance(transaction.currency)
        transactionNameText = transaction.name

        val numberFormat = currencyInteractor.getCurrencyNumberFormat(transaction.currency)
        // Remove all non breaking spaces & change commas when formatting the value
        transactionCurrentValueText = numberFormat.format(transaction.amount).replace("\u00A0", "")
        if (transactionCurrentValueText.contains(".")) {
            transactionCurrentValueText = transactionCurrentValueText.replace(",", "")
        } else {
            transactionCurrentValueText = transactionCurrentValueText.replace(",", ".")
        }

        if (transaction.imageUri != null) {
            transactionImageFile = File(transaction.imageUri)
        }
        transactionDate = transaction.date
        transactionDescription = transaction.description

        if (transaction.latitude != null && transaction.longitude != null) {
            transactionLocation = LatLng(transaction.latitude, transaction.longitude)
        }
        transactionSelectedCategory = transaction.category
    }

    /**
     * Saves the transaction
     */
    override fun saveTransaction(): Pair<Transaction, TransactionAction>? {
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

        transactionImageFile?.let { file ->
            transactionInteractor.updateTransactionImageUri(transaction, file.canonicalPath)
        }
    }

    /**
     * Format amount
     * @return The formatted amount
     */
    override fun formatValue(): String {
        when (keyboardType) {
            KeyboardType.Normal -> {
                if (transactionCurrentValueText.isEmpty()) {
                    return "0"
                }
                return transactionCurrentValueText
            }
            else -> Logger.t(InputMainPresenter.TAG).i("Non supported keyboard type")
        }
        return transactionCurrentValueText
    }
}