package com.pinup.pfm.ui.input.action.camera

import com.pinup.pfm.ui.core.view.BaseScreen
import java.io.File

/**
 * Screen actions for input action camera
 */
interface InputActionCameraScreen : BaseScreen {

    /**
     * Start new image capture into a file
     * @param file Where the image should be saved
     */
    fun startNewImageCapture(file: File)

    /**
     * Start new image capture failed
     */
    fun startNewImageCaptureFailed()

    /**
     * Callback of the image capture succeeded state
     * @param file The file where the image was saved
     */
    fun imageCaptureSucceeded(file: File)

    /**
     * Callback of the failure of image capture
     */
    fun imageCaptureFailed()
}