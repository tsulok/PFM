package com.pinup.pfm.ui.input.action.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.OnClick
import com.commonsware.cwac.cam2.CameraActivity
import com.commonsware.cwac.cam2.Facing
import com.commonsware.cwac.cam2.FlashMode
import com.commonsware.cwac.cam2.ZoomStyle
import com.orhanobut.logger.Logger
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.utils.ui.core.AlertHelper
import org.jetbrains.anko.imageURI
import org.jetbrains.anko.support.v4.find
import permissions.dispatcher.*
import java.io.File
import javax.inject.Inject


/**
 * Input action camera fragment
 */
@RuntimePermissions
class InputActionCameraFragment : BaseFragment(), InputActionCameraScreen {

    companion object {
        @JvmStatic val REQUEST_CODE_CAMERA = 1001
    }

    @Inject lateinit var inputActionCameraPresenter: InputActionCameraPresenter
    @Inject lateinit var alertHelper: AlertHelper

    val transactionPhotoImageView by lazy { find<ImageView>(R.id.actionCameraTransactionPhoto) }
    val noImageTxt by lazy { find<TextView>(R.id.actionCameraTransactionNoPhotoTxt) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_camera
    }

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun getPresenter(): IBasePresenter? = inputActionCameraPresenter
    override fun getScreen(): BaseScreen = this

    override fun initObjects(view: View?) {
        inputActionCameraPresenter.updateImage()
    }

    override fun initEventHandlers(view: View?) {

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
        InputActionCameraFragmentPermissionsDispatcher.cameraActionClickWithCheck(this)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun cameraActionClick() {
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
                .skipOrientationNormalization()
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

    //region Permission handling
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        InputActionCameraFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        alertHelper.createAlert(R.string.permission_camera_title,
                R.string.permission_camera_rationale_message)
                .positiveText(R.string.grant)
                .negativeText(R.string.decline)
                .onPositive({ dialog, which -> request.proceed() })
                .onNegative({ dialog, which -> request.cancel() })
                .show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun showNeverAskForCamera() {
        alertHelper.createAlert(R.string.permission_camera_title,
                R.string.permission_camera_rationale_message)
                .positiveText(R.string.got_it)
                .negativeText(R.string.cancel)
                .show()
    }
    //endregion
}