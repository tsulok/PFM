package com.pinup.pfm.ui.input.action.camera

import android.net.Uri
import com.pinup.pfm.common.ui.core.BaseScreen
import java.io.File

/**
 * Screen actions for input action camera
 */
interface InputActionCameraScreen: BaseScreen {

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
     * Callback of the image capture succeeded state
     * @param file The file where the image was saved
     */
    fun imageCaptureSucceeded(uri: Uri)

    /**
     * Callback of the failure of image capture
     */
    fun imageCaptureFailed()
}