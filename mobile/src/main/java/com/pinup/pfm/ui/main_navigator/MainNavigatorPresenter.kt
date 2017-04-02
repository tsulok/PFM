package com.pinup.pfm.ui.main_navigator

import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for main navigator
 */
class MainNavigatorPresenter @Inject constructor() : BasePresenter<MainNavigatorScreen>() {

    fun navigateToSettings() {
        screen?.navigateToSettings()
    }

    fun navigateToCharts() {
        screen?.navigateToCharts()
    }
}