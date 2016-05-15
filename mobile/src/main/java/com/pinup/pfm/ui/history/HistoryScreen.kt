package com.pinup.pfm.ui.history

import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.ui.core.view.BaseScreen

/**
 * Screen actions for history
 */
interface HistoryScreen : BaseScreen {

    fun loadSavedTransaction(transaction: Transaction)
}