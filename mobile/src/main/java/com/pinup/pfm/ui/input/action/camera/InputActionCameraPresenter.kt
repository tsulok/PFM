package com.pinup.pfm.ui.input.action.camera

import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.domain.manager.transaction.TransactionManager
import com.pinup.pfm.interactor.utils.StorageInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import java.io.File
import javax.inject.Inject

/**
 * Presenter for input action camera
 */
class InputActionCameraPresenter @Inject constructor(val storageInteractor: StorageInteractor,
                                                     val transactionManager: TransactionManager)
    : BasePresenter<InputActionCameraScreen>() {

    private var tempFile: File? = null

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
        tempFile = storageInteractor.createTemporaryFile()
        tempFile?.let { screen?.startNewImageCapture(it) }
    }

    /**
     * Handle the capture finished event
     * Search for the file where the temporary image is saved & notify the screen whether it was found or not
     */
    fun handleImageCaptureFinished() {
        tempFile?.let {
            screen?.imageCaptureSucceeded(it)
            transactionManager.transactionImageFile = it
        }
    }
}