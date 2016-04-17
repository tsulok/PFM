package com.pinup.pfm.ui.input.container

import com.pinup.pfm.ui.core.view.BasePresenter

/**
 * Presenter for input container
 */
class InputContainerPresenter : BasePresenter<InputContainerScreen>() {

    fun navigateToCharts() {
        screen?.navigateToCharts()
    }
}