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

    @Bind(R.id.inputAmountTxt) lateinit var amountText: TextView
    @Bind(R.id.inputActionPhoto) lateinit var actionPhotoButton: ImageButton
    @Bind(R.id.inputActionLocation) lateinit var actionLocationButton: ImageButton
    @Bind(R.id.inputActionDate) lateinit var actionDateButton: ImageButton
    @Bind(R.id.inputActionDescription) lateinit var actionDesciptionButton: ImageButton

    lateinit var openAction: OpenAction
    var isPageOpening = true

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_container
    }

    override fun initObjects(view: View?) {
        initSharedTransitionElements()
        changeToSelectedAction(openAction)
    }

    override fun initEventHandlers(view: View?) {

    }

    private fun initSharedTransitionElements() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            amountText.transitionName = SharedViewConstants.KEY_INPUT_AMOUNT_TXT
            actionPhotoButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_PHOTO_BUTTON
            actionLocationButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_LOCATION_BUTTON
            actionDesciptionButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_DESCRIPTION_BUTTON
            actionDateButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_DATE_BUTTON
        }
    }

    private fun highlightSelectedItem() {
        actionPhotoButton.isSelected = false
        actionDateButton.isSelected = false
        actionDesciptionButton.isSelected = false
        actionLocationButton.isSelected = false
        when (openAction) {
            OpenAction.Photo -> actionPhotoButton.isSelected = true
            OpenAction.Date -> actionDateButton.isSelected = true
            OpenAction.Description -> actionDesciptionButton.isSelected = true
            OpenAction.Location -> actionLocationButton.isSelected = true
        }
    }

    private fun changeToSelectedAction(openAction: OpenAction) {

        // Do nothing if the open action is currently selected
        if (this.openAction == openAction && !isPageOpening) {
            return
        }

        this.openAction = openAction
        highlightSelectedItem()

        var openableFragment: BaseFragment
        when (openAction) {
            OpenAction.Photo -> openableFragment = inputActionCameraFragment
            else -> openableFragment = inputActionCameraFragment
        }

        replaceFragment(childFragmentManager, R.id.inputActionContainer, openableFragment)
    }

    @OnClick(R.id.closeBtn)
    fun onCloseClicked() {
        finish()
    }
}