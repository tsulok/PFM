package com.pinup.pfm.ui.main_navigator

import com.pinup.pfm.ui.core.view.BaseScreen

/**
 * Interface for main navigator screen
 */
interface MainNavigatorScreen : BaseScreen {
    fun navigateToSettings()
    fun navigateToCharts()
}