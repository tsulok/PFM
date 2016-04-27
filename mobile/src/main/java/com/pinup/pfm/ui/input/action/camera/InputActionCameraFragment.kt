package com.pinup.pfm.ui.input.action.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import butterknife.Bind
import butterknife.OnClick
import com.commonsware.cwac.cam2.CameraActivity
import com.commonsware.cwac.cam2.Facing
import com.commonsware.cwac.cam2.ZoomStyle
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.interactor.utils.StorageInteractor
import com.pinup.pfm.ui.core.view.BaseFragment
import org.jetbrains.anko.imageURI
import javax.inject.Inject

/**
 * Input action camera fragment
 */
class InputActionCameraFragment: BaseFragment {

    companion object {
        @JvmStatic val REQUEST_CODE_CAMERA = 1001
    }

    @Inject lateinit var storageInteractor: StorageInteractor
    @Inject lateinit var inputActionCameraPresenter: InputActionCameraPresenter

    @Bind(R.id.actionCameraTransactionPhoto) lateinit var transactionPhotoImageView: ImageView

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_camera
    }

    override fun initObjects(view: View?) {

    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            Logger.d("InputMain activity result succeeded")
            when (requestCode) {
                REQUEST_CODE_CAMERA -> handleImageTaken()
                else -> super.onActivityResult(requestCode, resultCode, data)
            }
        } else {
            Logger.e("InputMain activity result failed")
        }
    }

    private fun handleImageTaken() {
        val file = storageInteractor.getFile(InputActionCameraPresenter.IMAGE_TRANSACTION_NAME)
        if (file != null) {
            transactionPhotoImageView.imageURI = Uri.fromFile(file)
        }
    }

    @OnClick(R.id.actionCameraTakePhoto)
    fun takePhotoClicked() {
        storageInteractor.createFile(InputActionCameraPresenter.IMAGE_TRANSACTION_NAME)
        val file = storageInteractor.getFile(InputActionCameraPresenter.IMAGE_TRANSACTION_NAME)

        if (file == null) {
            Logger.e("File couldn't be created in order to take picture")
            return
        }

        val cameraIntent = CameraActivity
                .IntentBuilder(activity)
                .skipConfirm()
                .to(file)
                .facing(Facing.BACK)
                .debug()
                .zoomStyle(ZoomStyle.PINCH)
                .updateMediaStore()
                .build()

        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA)
    }

    @OnClick(R.id.actionCameraExistingPhoto)
    fun existingPhotoChooserClicke() {
        makeToast("Gallery chooser will be here")
    }
}