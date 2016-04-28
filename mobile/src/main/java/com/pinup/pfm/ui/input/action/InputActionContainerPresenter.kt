package com.pinup.pfm.ui.input.action

import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.ui.core.view.BasePresenter

/**
 * Presenter for input action container
 */
class InputActionContainerPresenter : BasePresenter<InputActionContainerScreen> {

    lateinit var currentOpenAction: OpenAction
    private var isPageOpening = true

    constructor() : super() {

    }

    fun openAction(openAction: OpenAction) {
        // Do nothing if the open action is currently selected
        if (this.currentOpenAction == openAction && !isPageOpening) {
            return
        }

        this.currentOpenAction = openAction
        screen?.changeToSelectedAction(currentOpenAction)
    }
}