package com.pinup.pfm.ui.input.main

import android.os.Build
import android.text.Editable
import android.transition.TransitionInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import butterknife.Bind
import butterknife.OnClick
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.extensions.removeSoftKeboard
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.input.KeyboardAction
import com.pinup.pfm.model.input.OpenAction
import com.pinup.pfm.model.transaction.OnTransactionInteractionListener
import com.pinup.pfm.model.transaction.TransactionAction
import com.pinup.pfm.ui.MainActivity
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.SharedTransactionElementWrapper
import com.pinup.pfm.ui.input.action.InputActionContainerFragment
import com.pinup.pfm.ui.input.keyboard.KeyboardFragment
import com.pinup.pfm.utils.SharedViewConstants
import com.pinup.pfm.utils.helper.UIHelper
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.find
import java.util.*
import javax.inject.Inject

/**
 * Fragment for input main fragment
 */
class InputMainFragment : BaseFragment, InputMainScreen {

    @Inject lateinit var inputMainPresenter: InputMainPresenter
    @Inject lateinit var inputActionContainerFragment: InputActionContainerFragment

    val nameEditText by lazy { find<EditText>(R.id.inputNameTxt) }
    val amountTextView by lazy { find<TextView>(R.id.inputCurrencyTxt) }
    val currencyTextView by lazy { find<TextView>(R.id.inputAmountTxt) }
    val actionPhotoButton by lazy { find<ImageButton>(R.id.inputActionPhotoNew) }
    val actionLocationButton by lazy { find<ImageButton>(R.id.inputActionLocationNew) }
    val actionDescriptionButton by lazy { find<ImageButton>(R.id.inputActionDescriptionNew) }
    val actionDateButton by lazy { find<ImageButton>(R.id.inputActionDateNew) }

    lateinit var keyboardFragment: KeyboardFragment

    var onTransactionInteractionListener: OnTransactionInteractionListener? = null

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_main
    }

    override fun initObjects(view: View?) {
        inputMainPresenter.bind(this)

        keyboardFragment = KeyboardFragment.newInstance(inputMainPresenter.getSelectedKeyboardType())

        replaceFragment(childFragmentManager, R.id.keyboardContainer,
                keyboardFragment, keyboardFragment.javaClass.canonicalName)

        inputMainPresenter.loadCurrentlySelectedCurrency()
        initSharedTransitions()

        inputMainPresenter.loadCurrentValue()
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

        nameEditText.setOnFocusChangeListener { view, hasFocus -> if (!hasFocus) view.removeSoftKeboard(context) }
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

    @OnClick(R.id.inputActionPhotoNew)
    fun onPhotoImageClicked() {
        openActionPage(OpenAction.Photo)
    }

    @OnClick(R.id.inputActionDateNew)
    fun onDateImageClicked() {
        openActionPage(OpenAction.Date)
    }

    @OnClick(R.id.inputActionLocationNew)
    fun onLocationImageClicked() {
        openActionPage(OpenAction.Location)
    }

    @OnClick(R.id.inputActionDescriptionNew)
    fun onDescriptionImageClicked() {
        openActionPage(OpenAction.Description)
    }

    @OnClick(R.id.inputKeyboardChangeBtn)
    fun onKeyboardChangeClicked() {
        makeToast("Keyboard change click")
    }

    @OnClick(R.id.inputSubmitBtn)
    fun onSubmitClicked() {
        inputMainPresenter.saveTransaction(nameEditText.text.toString())
    }

    /**
     * Init views for shared transition
     */
    private fun initSharedTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            amountTextView.transitionName = SharedViewConstants.KEY_INPUT_AMOUNT_TXT
            actionPhotoButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_PHOTO_BUTTON
            actionDateButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_DATE_BUTTON
            actionDescriptionButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_DESCRIPTION_BUTTON
            actionLocationButton.transitionName = SharedViewConstants.KEY_INPUT_ACTION_LOCATION_BUTTON

            // Init fragment transitions for action page
            val transitionInflater = TransitionInflater.from(activity)
            sharedElementReturnTransition = transitionInflater.inflateTransition(R.transition.all_transition)
            exitTransition = transitionInflater.inflateTransition(android.R.transition.fade)
            enterTransition = transitionInflater.inflateTransition(android.R.transition.fade)

            inputActionContainerFragment.sharedElementEnterTransition = transitionInflater.inflateTransition(R.transition.all_transition)
            inputActionContainerFragment.enterTransition = transitionInflater.inflateTransition(android.R.transition.fade)
            inputActionContainerFragment.exitTransition = transitionInflater.inflateTransition(android.R.transition.fade)

            inputActionContainerFragment.allowEnterTransitionOverlap = false
            inputActionContainerFragment.allowReturnTransitionOverlap = false
        }
    }

    /**
     * Open action page
     */
    private fun openActionPage(openAction: OpenAction) {
        inputActionContainerFragment.setupInitialOpenAction(openAction)

        (activity as MainActivity).switchToFragmentWithTransition(
                inputActionContainerFragment,
                false,
                SharedTransactionElementWrapper(amountTextView, SharedViewConstants.KEY_INPUT_AMOUNT_TXT),
                SharedTransactionElementWrapper(actionPhotoButton, SharedViewConstants.KEY_INPUT_ACTION_PHOTO_BUTTON),
                SharedTransactionElementWrapper(actionLocationButton, SharedViewConstants.KEY_INPUT_ACTION_LOCATION_BUTTON),
                SharedTransactionElementWrapper(actionDescriptionButton, SharedViewConstants.KEY_INPUT_ACTION_DESCRIPTION_BUTTON),
                SharedTransactionElementWrapper(actionDateButton, SharedViewConstants.KEY_INPUT_ACTION_DATE_BUTTON)
        )
    }

    /**
     * Reloads the selected transaction to the view
     */
    fun reloadTransaction() {
        inputMainPresenter.loadCurrentlySelectedCurrency()
        inputMainPresenter.loadCurrentValue()
        nameEditText.setText(inputMainPresenter.getCurrentTransactionName())
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

    override fun showMissingTransactionArgument(message: String) {
        makeToast(message)
    }

    override fun transactionSaved(transaction: Transaction, action: TransactionAction) {
        makeToast("Transaction saved")
        nameEditText.text.clear()
        amountTextView.text = "0"
        inputMainPresenter.reset()

        when (action) {
            TransactionAction.NEW -> onTransactionInteractionListener?.onTransactionAdded(transaction)
            TransactionAction.DELETED -> onTransactionInteractionListener?.onTransactionDeleted(transaction)
            TransactionAction.MODIFIED -> onTransactionInteractionListener?.onTransactionEdited(transaction)
        }
    }

    override fun transactionSaveFailed() {
        makeToast("Transaction save failed")
    }

    //endregion
}