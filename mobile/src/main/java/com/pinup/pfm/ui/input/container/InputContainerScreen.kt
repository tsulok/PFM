package com.pinup.pfm.ui.input.container

import com.pinup.pfm.common.ui.core.BaseScreen

/**
 * Screen actions for input container
 */
interface InputContainerScreen : BaseScreen {

    /**
     * Navigates the screen to the charts screen
     */
    fun navigateToCharts()

    /**
     * Navigate the screen to the settings
     */
    fun navigateToSettings()

    /**
     * Open transaction history
     */
    fun openTransactionHistory()
}