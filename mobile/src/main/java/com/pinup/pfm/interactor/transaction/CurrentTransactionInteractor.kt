package com.pinup.pfm.interactor.transaction

import com.google.android.gms.maps.model.LatLng
import com.orhanobut.logger.Logger
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.ui.input.main.InputMainPresenter
import java.io.File
import java.util.*

/**
 * Current transaction interactor
 */
class CurrentTransactionInteractor {

    var keyboardType: KeyboardType = KeyboardType.Normal

    var transactionCurrentValueText: String = ""
    var transactionImageFile: File? = null
    var transactionDate: Date = Date()
    var transactionDescription: String = ""
    var transactionSelectedCategory: Category? = null
    var transactionLocation: LatLng? = null
    var transactionCurrency: Currency? = null

    fun resetTransaction() {
        transactionCurrentValueText = ""
        transactionImageFile = null
        transactionDate = Date()
        transactionDescription = ""
        transactionSelectedCategory = null
        transactionLocation = null
        transactionCurrency = null
    }

    fun saveTransaction() {
        // TODO finalize the save
    }

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