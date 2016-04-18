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
import com.pinup.pfm.utils.helper.UIHelper
import java.util.*
import javax.inject.Inject

/**
 * Fragment for input main fragment
 */
class InputMainFragment : BaseFragment, InputMainScreen {

    @Inject lateinit var inputMainPresenter: InputMainPresenter

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
        inputMainPresenter.bind(this)

        keyboardFragment = KeyboardFragment.newInstance(KeyboardType.Normal)

        replaceFragment(childFragmentManager, R.id.keyboardContainer,
                keyboardFragment, keyboardFragment.javaClass.canonicalName)

//        inputMainPresenter.loadCurrentlySelectedCurrency()
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

    override fun onDestroyView() {
        inputMainPresenter.unbind()
        super.onDestroyView()
    }

    @OnClick(R.id.inputCurrencyTxt)
    fun onCurrencyChangeClicked() {
        inputMainPresenter.showSupportedCurrencies()
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

    override fun showSupportedCurrencies(selectedCurrency: Currency?, availableCurrencies: List<Currency>) {

        val currentIndex = availableCurrencies.indexOf(selectedCurrency)
        UIHelper.instance.createDefaultDialog(activity)
                .title(R.string.input_currency_chooser)
                .items(availableCurrencies)
                .itemsCallbackSingleChoice(currentIndex)
                    {   dialog, itemView, which, text ->
                        inputMainPresenter.updateSelectedCurrency(availableCurrencies[which])
                        true
                    }
                .show()
    }

    override fun updateSelectedCurrency(currency: Currency?) {
        // TODO textView is not inflated with Butterknife
//        currencyTextView.text = currency?.currencyCode ?: "-"
    }
}