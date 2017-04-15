package com.pinup.pfm.ui.input.action.camera

import android.net.Uri
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.interactor.utils.IStorageInteractor
import com.pinup.pfm.common.ui.core.BasePresenter
import java.io.File
import javax.inject.Inject

/**
 * Presenter for input action camera
 */
class InputActionCameraPresenter @Inject constructor(val storageInteractor: IStorageInteractor,
                                                     val transactionManager: ITransactionManager)
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
        tempFile?.let { file ->
            screen?.imageCaptureSucceeded(file)
            transactionManager.transactionImageFile = file
        }
    }

    fun handleGalleryImageChosen(selectedImages: List<Uri>) {
        // Only 1 image selection is possible
        selectedImages.firstOrNull()?.let { image ->
            transactionManager.transactionImageFile = File(image.path)
            screen?.imageCaptureSucceeded(image)
        }
    }
}