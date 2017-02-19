package com.pinup.pfm.ui.input.action

import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input action container
 */
class InputActionContainerPresenter @Inject constructor(val currentTransactionInteractor: CurrentTransactionInteractor)
    : BasePresenter<InputActionContainerScreen>() {

    var currentOpenAction: OpenAction? = null
    private var isPageOpening = true

    fun openAction(openAction: OpenAction) {
        // Do nothing if the open action is currently selected
        if (this.currentOpenAction == openAction && !isPageOpening) {
            return
        }

        this.currentOpenAction = openAction

        currentOpenAction?.let {
            screen?.changeToSelectedAction(openAction)
        }
    }

    fun getFormattedAmountText(): String {
        return currentTransactionInteractor.formatValue()
    }
}