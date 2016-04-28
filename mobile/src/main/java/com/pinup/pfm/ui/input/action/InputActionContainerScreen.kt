package com.pinup.pfm.ui.input.action

import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.ui.core.view.BaseScreen

/**
 * Screen actions for input action container
 */
interface InputActionContainerScreen : BaseScreen {

    /**
     * Load container fragment with the appropriate action
     * @param openAction action to be opened
     */
    fun changeToSelectedAction(openAction: OpenAction)
}