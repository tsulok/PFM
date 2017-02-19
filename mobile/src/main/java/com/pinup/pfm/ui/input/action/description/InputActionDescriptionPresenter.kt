package com.pinup.pfm.ui.input.action.description

import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input action description
 */
class InputActionDescriptionPresenter @Inject constructor(val currentTransactionInteractor: CurrentTransactionInteractor) : BasePresenter<InputActionDescriptionScreen>() {

    var text: String = ""

    init {
        text = currentTransactionInteractor.transactionDescription
    }

    fun updateDescription() {
        screen?.updateDescriptionText(text)
    }

    fun updateDescriptionText(descriptionText: String) {
        text = descriptionText
        currentTransactionInteractor.transactionDescription = text
    }
}