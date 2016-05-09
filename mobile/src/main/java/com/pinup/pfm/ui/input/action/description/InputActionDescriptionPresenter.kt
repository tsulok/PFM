package com.pinup.pfm.ui.input.action.description

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input action description
 */
class InputActionDescriptionPresenter : BasePresenter<InputActionDescriptionScreen> {

    @Inject lateinit var currentTransactionInteractor: CurrentTransactionInteractor

    var text: String = ""

    constructor() : super() {
        PFMApplication.injector.inject(this)
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