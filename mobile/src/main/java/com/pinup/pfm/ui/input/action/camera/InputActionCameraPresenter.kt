package com.pinup.pfm.ui.input.action.camera

import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.domain.manager.transaction.TransactionManager
import com.pinup.pfm.interactor.utils.StorageInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for input action camera
 */
class InputActionCameraPresenter @Inject constructor(val storageInteractor: StorageInteractor,
                                                     val transactionManager: TransactionManager)
    : BasePresenter<InputActionCameraScreen>() {

    companion object {
        @JvmStatic val IMAGE_TRANSACTION_NAME = "transactionPicture.jpg"
        @JvmStatic val IMAGE_TMP_TRANSACTION_NAME = "transactionPictureTmp.jpg"
    }

    /**
     * Update the image if any
     */
    fun updateImage() {
        val file = transactionManager.transactionImageFile
        if (file != null) {
            screen?.imageCaptureSucceeded(file)
        }
    }

    /**
     * Starts the image capture
     */
    fun startImageCapture() {
        storageInteractor.createFile(InputActionCameraPresenter.IMAGE_TMP_TRANSACTION_NAME)
        val file = storageInteractor.getFile(InputActionCameraPresenter.IMAGE_TMP_TRANSACTION_NAME)

        if (file == null) {
            Logger.e("File couldn't be created in order to take picture")
            screen?.startNewImageCaptureFailed()
            return
        }

        screen?.startNewImageCapture(file)
    }

    /**
     * Handle the capture finished event
     * Search for the file where the temporary image is saved & notify the screen whether it was found or not
     */
    fun handleImageCaptureFinished() {
        val file = storageInteractor.getFile(InputActionCameraPresenter.IMAGE_TMP_TRANSACTION_NAME)
        if (file == null) {
            screen?.imageCaptureFailed()
            transactionManager.transactionImageFile = null
            return
        }

        screen?.imageCaptureSucceeded(file)
        transactionManager.transactionImageFile = file
    }
}