package com.pinup.pfm.ui.input.action

import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import butterknife.Bind
import butterknife.OnClick
import butterknife.bindView
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.input.action.camera.InputActionCameraFragment
import com.pinup.pfm.utils.SharedViewConstants
import javax.inject.Inject

/**
 * Input action fragment
 */
class InputActionContainerFragment : BaseFragment, InputActionContainerScreen {

    @Inject lateinit var inputActionCameraFragment: InputActionCameraFragment
    @Inject lateinit var inputActionContainerPresenter: InputActionContainerPresenter

    @Bind(R.id.inputActionContainerAmountTxt) lateinit var amountText: TextView
    @Bind(R.id.inputActionContainerPhoto) lateinit var actionPhotoButton: ImageButton
    @Bind(R.id.inputActionContainerLocation) lateinit var actionLocationButton: ImageButton
    @Bind(R.id.inputActionContainerDate) lateinit var actionDateButton: ImageButton
    @Bind(R.id.inputActionContainerDescription) lateinit var actionDesciptionButton: ImageButton

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_container
    }

    override fun initObjects(view: View?) {
        inputActionContainerPresenter.bind(this)
        initSharedTransitionElements()
        inputActionContainerPresenter.openAction(inputActionContainerPresenter.currentOpenAction)
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        inputActionContainerPresenter.unbind()
        super.onDestroyView()
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

    /**
     * Set up initial open action
     */
    fun setupInitialOpenAction(openAction: OpenAction) {
        inputActionContainerPresenter.currentOpenAction = openAction
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
        inputActionContainerPresenter.openAction(OpenAction.Location)
    }

    //region Screen actions
    /**
     * Load container fragment with the appropriate action
     * @param openAction action to be opened
     */
    override fun changeToSelectedAction(openAction: OpenAction) {
        highlightSelectedItem()

        var openableFragment: BaseFragment
        when (openAction) {
            OpenAction.Photo -> openableFragment = inputActionCameraFragment
            else -> openableFragment = inputActionCameraFragment
        }

        replaceFragment(childFragmentManager, R.id.inputActionContainer, openableFragment)
    }
    //endregion
}