package com.pinup.pfm.ui.input.action

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.ui.core.view.BasePresenter
import com.pinup.pfm.ui.input.action.description.InputActionDescriptionPresenter
import javax.inject.Inject

/**
 * Presenter for input action container
 */
class InputActionContainerPresenter : BasePresenter<InputActionContainerScreen> {

    lateinit var currentOpenAction: OpenAction
    private var isPageOpening = true

    @Inject lateinit var currentTransactionInteractor: CurrentTransactionInteractor

    constructor() : super() {
        PFMApplication.injector.inject(this)
    }

    fun openAction(openAction: OpenAction) {
        // Do nothing if the open action is currently selected
        if (this.currentOpenAction == openAction && !isPageOpening) {
            return
        }

        this.currentOpenAction = openAction
        screen?.changeToSelectedAction(currentOpenAction)
    }

    fun getFormattedAmountText(): String {
        return currentTransactionInteractor.formatValue()
    }
}