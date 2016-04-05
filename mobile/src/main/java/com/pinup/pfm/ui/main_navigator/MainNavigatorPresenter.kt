package com.pinup.pfm.ui.main_navigator

import com.pinup.pfm.ui.core.view.BasePresenter

/**
 * Presenter for main navigator
 */
class MainNavigatorPresenter() : BasePresenter<MainNavigatorScreen>() {

    fun navigateToSettings() {
        screen?.navigateToSettings()
    }

    fun navigateToCharts() {
        screen?.navigateToCharts()
    }
}