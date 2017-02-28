package com.pinup.pfm.ui.input.action

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import butterknife.OnClick
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.input.action.camera.InputActionCameraFragment
import com.pinup.pfm.ui.input.action.date.InputActionDateFragment
import com.pinup.pfm.ui.input.action.description.InputActionDescriptionFragment
import com.pinup.pfm.ui.input.action.location.InputActionLocationFragment
import com.pinup.pfm.utils.SharedViewConstants
import com.pinup.pfm.utils.ui.core.AlertHelper
import org.jetbrains.anko.support.v4.find
import permissions.dispatcher.*
import javax.inject.Inject

/**
 * Input action fragment
 */
@FragmentWithArgs
@RuntimePermissions
class InputActionContainerFragment
    : BaseFragment(), InputActionContainerScreen {

    @Inject lateinit var inputActionContainerPresenter: InputActionContainerPresenter
    @Inject lateinit var alertHelper: AlertHelper

    @Arg
    lateinit var openAction: OpenAction

    val inputActionCameraFragment: InputActionCameraFragment by lazy { InputActionCameraFragment() }
    val inputActionLocationFragment: InputActionLocationFragment by lazy { InputActionLocationFragment() }
    val inputActionDescriptionFragment: InputActionDescriptionFragment by lazy { InputActionDescriptionFragment() }
    val inputActionDateFragment: InputActionDateFragment by lazy { InputActionDateFragment() }

    val amountText by lazy { find<TextView>(R.id.inputActionContainerAmountTxt) }
    val actionPhotoButton by lazy { find<ImageButton>(R.id.inputActionContainerPhoto) }
    val actionLocationButton by lazy { find<ImageButton>(R.id.inputActionContainerLocation) }
    val actionDateButton by lazy { find<ImageButton>(R.id.inputActionContainerDate) }
    val actionDesciptionButton by lazy { find<ImageButton>(R.id.inputActionContainerDescription) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentArgs.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_container
    }

    override fun getPresenter(): IBasePresenter? = inputActionContainerPresenter
    override fun getScreen(): BaseScreen = this

    override fun initObjects(view: View?) {
        initSharedTransitionElements()
        inputActionContainerPresenter.openAction(openAction)

        amountText.text = inputActionContainerPresenter.getFormattedAmountText()
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    /**
     * Initialize shared transition elements
     */
    private fun initSharedTransitionElements() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            amountText.transitionName = SharedViewConstants.KEY_INPUT_AMOUNT_TXT
            actionPhotoButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_PHOTO_BUTTON
            actionLocationButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_LOCATION_BUTTON
            actionDesciptionButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_DESCRIPTION_BUTTON
            actionDateButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_DATE_BUTTON
        }
    }

    /**
     * Highlight items according opened action
     */
    private fun highlightSelectedItem() {
        actionPhotoButton.isSelected = false
        actionDateButton.isSelected = false
        actionDesciptionButton.isSelected = false
        actionLocationButton.isSelected = false
        when (inputActionContainerPresenter.currentOpenAction) {
            OpenAction.Photo -> actionPhotoButton.isSelected = true
            OpenAction.Date -> actionDateButton.isSelected = true
            OpenAction.Description -> actionDesciptionButton.isSelected = true
            OpenAction.Location -> actionLocationButton.isSelected = true
        }
    }

    @OnClick(R.id.closeBtn)
    fun onCloseClicked() {
        finish()
    }

    @OnClick(R.id.inputActionContainerPhoto)
    fun onPhotoClicked() {
        inputActionContainerPresenter.openAction(OpenAction.Photo)
    }

    @OnClick(R.id.inputActionContainerDescription)
    fun onDescriptionClicked() {
        inputActionContainerPresenter.openAction(OpenAction.Description)
    }

    @OnClick(R.id.inputActionContainerDate)
    fun onDateClicked() {
        inputActionContainerPresenter.openAction(OpenAction.Date)
    }

    @OnClick(R.id.inputActionContainerLocation)
    fun onLocationClicked() {
        InputActionContainerFragmentPermissionsDispatcher.openLocationViewWithCheck(this)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun openLocationView() {
        inputActionContainerPresenter.openAction(OpenAction.Location)
    }

    //region Screen actions
    /**
     * Load container fragment with the appropriate action
     * @param openAction action to be opened
     */
    override fun changeToSelectedAction(openAction: OpenAction) {
        highlightSelectedItem()

        val openableFragment: BaseFragment
        when (openAction) {
            OpenAction.Photo -> openableFragment = inputActionCameraFragment
            OpenAction.Description -> openableFragment = inputActionDescriptionFragment
            OpenAction.Location -> openableFragment = inputActionLocationFragment
            OpenAction.Date -> openableFragment = inputActionDateFragment

        }

        replaceFragment(childFragmentManager, R.id.inputActionContainer, openableFragment)
    }
    //endregion

    //region Permission handling
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        InputActionContainerFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForCamera(request: PermissionRequest) {
        alertHelper.createAlert(R.string.permission_location_title,
                R.string.permission_location_rationale_message)
                .positiveText(R.string.grant)
                .negativeText(R.string.decline)
                .onPositive({ dialog, which -> request.proceed() })
                .onNegative({ dialog, which -> request.cancel() })
                .show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showNeverAskForCamera() {
        alertHelper.createAlert(R.string.permission_location_title,
                R.string.permission_location_neveragain_message)
                .positiveText(R.string.got_it)
                .negativeText(R.string.cancel)
                .show()
    }
    //endregion
}