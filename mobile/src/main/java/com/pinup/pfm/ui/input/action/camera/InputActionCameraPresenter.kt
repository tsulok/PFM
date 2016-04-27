package com.pinup.pfm.ui.input.action.camera

import com.pinup.pfm.ui.core.view.BasePresenter

/**
 * Presenter for input action camera
 */
class InputActionCameraPresenter : BasePresenter<InputActionCameraScreen>() {

    companion object {
        @JvmStatic val IMAGE_TRANSACTION_NAME = "transactionPicture"
    }
}