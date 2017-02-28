package com.pinup.pfm.ui.input.action.description

import com.pinup.pfm.domain.manager.transaction.TransactionManager
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input action description
 */
class InputActionDescriptionPresenter @Inject constructor(val transactionManager: TransactionManager) : BasePresenter<InputActionDescriptionScreen>() {

    var text: String = ""

    init {
        text = transactionManager.transactionDescription
    }

    fun updateDescription() {
        screen?.updateDescriptionText(text)
    }

    fun updateDescriptionText(descriptionText: String) {
        text = descriptionText
        transactionManager.transactionDescription = text
    }
}