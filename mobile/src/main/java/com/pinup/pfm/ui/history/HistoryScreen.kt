package com.pinup.pfm.ui.history

import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.model.database.Transaction

/**
 * Screen actions for history
 */
interface HistoryScreen: BaseScreen {

    fun loadSavedTransaction(transaction: Transaction)
}