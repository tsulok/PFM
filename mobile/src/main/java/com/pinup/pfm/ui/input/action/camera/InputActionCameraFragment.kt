package com.pinup.pfm.ui.input.action.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.OnClick
import com.commonsware.cwac.cam2.CameraActivity
import com.commonsware.cwac.cam2.Facing
import com.commonsware.cwac.cam2.FlashMode
import com.commonsware.cwac.cam2.ZoomStyle
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.interactor.utils.StorageInteractor
import com.pinup.pfm.ui.core.view.BaseFragment
import org.jetbrains.anko.imageURI
import java.io.File
import javax.inject.Inject

/**
 * Input action camera fragment
 */
class InputActionCameraFragment: BaseFragment, InputActionCameraScreen {

    companion object {
        @JvmStatic val REQUEST_CODE_CAMERA = 1001
    }

    @Inject lateinit var inputActionCameraPresenter: InputActionCameraPresenter
    @Inject lateinit var storageInteractor: StorageInteractor

    @Bind(R.id.actionCameraTransactionPhoto) lateinit var transactionPhotoImageView: ImageView
    @Bind(R.id.actionCameraTransactionNoPhotoTxt) lateinit var noImageTxt: TextView

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_camera
    }

    override fun initObjects(view: View?) {
        inputActionCameraPresenter.bind(this)
        inputActionCameraPresenter.updateImage()
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        inputActionCameraPresenter.unbind()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            Logger.d("InputMain activity result succeeded")
            when (requestCode) {
                REQUEST_CODE_CAMERA -> inputActionCameraPresenter.handleImageCaptureFinished()
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        } else {
            Logger.e("InputMain activity result failed")
            when (requestCode) {
                REQUEST_CODE_CAMERA -> imageCaptureFailed()
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    @OnClick(R.id.actionCameraTakePhoto)
    fun takePhotoClicked() {
        inputActionCameraPresenter.startImageCapture()
    }

    @OnClick(R.id.actionCameraExistingPhoto)
    fun existingPhotoChooserClicked() {
        makeToast("Gallery chooser will be here")
    }

    //region Screen actions
    override fun startNewImageCapture(file: File) {
        val cameraIntent = CameraActivity
                .IntentBuilder(activity)
                .skipConfirm()
                .to(file)
                .facing(Facing.BACK)
                .debug()
                .zoomStyle(ZoomStyle.PINCH)
                .updateMediaStore()
                .flashMode(FlashMode.AUTO)
                .build()

        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA)
    }

    override fun startNewImageCaptureFailed() {
        makeToast("Image capture start failed. Try again")
    }

    override fun imageCaptureSucceeded(file: File) {
        // We need to set null to the uri, because the imageView won't refresh itself if the uri is the same
        transactionPhotoImageView.imageURI = null
        transactionPhotoImageView.imageURI = Uri.fromFile(file)
        noImageTxt.visibility = View.GONE
    }

    override fun imageCaptureFailed() {
        makeToast("Image capture save failed. Try again")
        noImageTxt.visibility = View.VISIBLE
    }

    //endregion
}