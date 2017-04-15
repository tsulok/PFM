package com.pinup.pfm.ui.input.action

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.f2prateek.dart.Dart
import com.f2prateek.dart.InjectExtra
import com.pinup.pfm.R
import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.di.component.PFMActivityComponent
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.ui.core.view.BaseActivity
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.common.ui.core.IBasePresenter
import com.pinup.pfm.ui.input.action.camera.InputActionCameraFragment
import com.pinup.pfm.ui.input.action.date.InputActionDateFragment
import com.pinup.pfm.ui.input.action.description.InputActionDescriptionFragment
import com.pinup.pfm.ui.input.action.location.InputActionLocationFragment
import com.pinup.pfm.utils.SharedViewConstants
import com.pinup.pfm.utils.ui.core.AlertHelper
import kotlinx.android.synthetic.main.fragment_input_action_container.*
import org.jetbrains.anko.find
import permissions.dispatcher.*
import javax.inject.Inject

/**
 * Input action fragment
 */
class InputActionContainerActivity
    : BaseActivity(), InputActionContainerScreen {

    @Inject lateinit var inputActionContainerPresenter: InputActionContainerPresenter
    @Inject lateinit var alertHelper: AlertHelper

    @InjectExtra
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
        Dart.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun loadContentId(): Int = R.layout.fragment_input_action_container

    override fun getActivityMainContainer(): Int = R.id.inputActionContainer

    override fun injectActivity(component: PFMActivityComponent) {
        component.inject(this)
    }

    override fun getPresenter(): IBasePresenter? = inputActionContainerPresenter
    override fun getScreen(): BaseScreen = this

    override fun initObjects() {
        initSharedTransitionElements()
        inputActionContainerPresenter.openAction(openAction)

        amountText.text = inputActionContainerPresenter.getFormattedAmountText()
        initEventHandlers()
    }

    private fun initEventHandlers() {
        closeBtn.setOnClickListener { finish() }

        actionPhotoButton.setOnClickListener { inputActionContainerPresenter.openAction(OpenAction.Photo) }

        actionDesciptionButton.setOnClickListener { inputActionContainerPresenter.openAction(OpenAction.Description) }

        actionDateButton.setOnClickListener { inputActionContainerPresenter.openAction(OpenAction.Date) }

        actionLocationButton.setOnClickListener { inputActionContainerPresenter.openAction(OpenAction.Location) }
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

        openableFragment.replaceFragment(supportFragmentManager, getActivityMainContainer())
    }
    //endregion

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            getActiveBaseFragment()?.onActivityResult(requestCode, resultCode, data)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}