package com.pinup.pfm.ui.input.main

import android.view.View
import android.widget.TextView
import butterknife.OnClick
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.model.input.KeyboardAction
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.input.keyboard.KeyboardFragment
import com.pinup.pfm.utils.helper.UIHelper
import org.jetbrains.anko.support.v4.act
import java.util.*
import javax.inject.Inject

/**
 * Fragment for input main fragment
 */
class InputMainFragment : BaseFragment, InputMainScreen {

    @Inject lateinit var inputMainPresenter: InputMainPresenter

    lateinit var nameTextView: TextView
    lateinit var amountTextView: TextView
    lateinit var currencyTextView: TextView

    lateinit var keyboardFragment: KeyboardFragment

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_main
    }

    override fun initObjects(view: View?) {
        inputMainPresenter.bind(this)

        // TODO figure out why view binding is not working in this fragment
        if (view != null) {
            currencyTextView = view.findViewById(R.id.inputCurrencyTxt) as TextView
            amountTextView = view.findViewById(R.id.inputAmountTxt) as TextView
            nameTextView = view.findViewById(R.id.inputNameTxt) as TextView
        }


        keyboardFragment = KeyboardFragment.newInstance(inputMainPresenter.keyboardType)

        replaceFragment(childFragmentManager, R.id.keyboardContainer,
                keyboardFragment, keyboardFragment.javaClass.canonicalName)

        inputMainPresenter.loadCurrentlySelectedCurrency()
    }

    override fun initEventHandlers(view: View?) {
        keyboardFragment.onActionListener = object : KeyboardFragment.OnKeyboardActionListener {
            override fun onKeyboardValuePressed(value: Double) {
                inputMainPresenter.addValue(value)
            }

            override fun onKeyboardActionPressed(action: KeyboardAction) {
                makeToast("Action pressed")
                when (action) {
                    KeyboardAction.Delete -> inputMainPresenter.removeLastDigit()
                    KeyboardAction.Dot -> inputMainPresenter.addDecimalPlace()
                    else -> Logger.d("Nothing to do here")
                }
            }
        }
    }

    override fun onDestroyView() {
        inputMainPresenter.unbind()
        super.onDestroyView()
    }

    // Onclick binding works...
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

    //region Screen implementations
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
        currencyTextView.text = currency?.currencyCode ?: "-"
    }

    override fun updateValue(value: String) {
        amountTextView.text = value
    }

    //endregion
}