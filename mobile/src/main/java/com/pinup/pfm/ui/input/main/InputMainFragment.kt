package com.pinup.pfm.ui.input.main

import android.view.View
import android.widget.TextView
import butterknife.Bind
import butterknife.OnClick
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.model.input.KeyboardAction
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.input.keyboard.KeyboardFragment

/**
 * Fragment for input main fragment
 */
class InputMainFragment : BaseFragment {

    @Bind(R.id.inputNameTxt) lateinit var nameTextView: TextView
    @Bind(R.id.inputAmountTxt) lateinit var amountTextView: TextView
    @Bind(R.id.inputCurrencyTxt) lateinit var currencyTextView: TextView

    lateinit var keyboardFragment: KeyboardFragment

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_main
    }

    override fun initObjects(view: View?) {
        keyboardFragment = KeyboardFragment.newInstance(KeyboardType.Normal)

        replaceFragment(childFragmentManager, R.id.keyboardContainer,
                keyboardFragment, keyboardFragment.javaClass.canonicalName)
    }

    override fun initEventHandlers(view: View?) {
        keyboardFragment.onActionListener = object : KeyboardFragment.OnKeyboardActionListener {
            override fun onKeyboardValuePressed(value: Double) {
                makeToast("Value: $value")
            }

            override fun onKeyboardActionPressed(action: KeyboardAction) {
                makeToast("Action pressed")
            }
        }
    }

    @OnClick(R.id.inputActionPhoto)
    fun onPhotoClicked() {
        makeToast("Photo click")
    }

    @OnClick(R.id.inputActionDate)
    fun onHistoryClicked() {
        makeToast("Date click")
    }

    @OnClick(R.id.inputActionLocation)
    fun onLocationClicked() {
        makeToast("Location click")
    }

    @OnClick(R.id.inputActionDescription)
    fun onDescriptionClicked() {
        makeToast("Description click")
    }

    @OnClick(R.id.inputKeyboardChangeBtn)
    fun onKeyboardChangeClicked() {
        makeToast("Keyboard change click")
    }

    @OnClick(R.id.inputSubmitBtn)
    fun onSubmitClicked() {
        makeToast("Submit click")
    }
}