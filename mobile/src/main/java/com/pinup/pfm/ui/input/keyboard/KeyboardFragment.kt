package com.pinup.pfm.ui.input.keyboard

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import butterknife.Bind
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.model.input.KeyboardAction
import com.pinup.pfm.model.input.KeyboardData
import com.pinup.pfm.model.input.KeyboardType
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.EmptyScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.find

/**
 * Fragment of keyboard
 */
class KeyboardFragment : BaseFragment(), EmptyScreen {

    companion object {
        @JvmStatic val KEY_TYPE: String = "KeyType"

        @JvmStatic fun newInstance(keyboardType: KeyboardType): KeyboardFragment {
            val keyboardFragment: KeyboardFragment = KeyboardFragment()
            val bundle: Bundle = Bundle()
            bundle.putInt(KEY_TYPE, keyboardType.ordinal)
            keyboardFragment.arguments = bundle
            return keyboardFragment
        }
    }

    val input0 by lazy { find<Button>(R.id.input0) }
    val input1 by lazy { find<Button>(R.id.input1) }
    val input2 by lazy { find<Button>(R.id.input2) }
    val input3 by lazy { find<Button>(R.id.input3) }
    val input4 by lazy { find<Button>(R.id.input4) }
    val input5 by lazy { find<Button>(R.id.input5) }
    val input6 by lazy { find<Button>(R.id.input6) }
    val input7 by lazy { find<Button>(R.id.input7) }
    val input8 by lazy { find<Button>(R.id.input8) }
    val input9 by lazy { find<Button>(R.id.input9) }
    val inputDot by lazy { find<Button>(R.id.inputComma) }
    val inputBack by lazy { find<ImageButton>(R.id.inputBack) }

    // Selected keyboardType
    var keyboardType: KeyboardType = KeyboardType.Normal
    var onActionListener: OnKeyboardActionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val asd = arguments.getInt(KEY_TYPE)
    }

    /**
     * Get layout depending on the type of the keyboard
     */
    override fun getLayoutId(): Int {
        return R.layout.layout_keyboard
    }

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun getPresenter(): IBasePresenter? = null
    override fun getScreen(): BaseScreen = this

    override fun initObjects(view: View?) {
        // TODO change values on keyboard according to keyboard type
        setUpKeyboard()
    }

    override fun initEventHandlers(view: View?) {
        input0.onClick { keyboardActionHandler(input0) }
        input1.onClick { keyboardActionHandler(input1) }
        input2.onClick { keyboardActionHandler(input2) }
        input3.onClick { keyboardActionHandler(input3) }
        input4.onClick { keyboardActionHandler(input4) }
        input5.onClick { keyboardActionHandler(input5) }
        input6.onClick { keyboardActionHandler(input6) }
        input7.onClick { keyboardActionHandler(input7) }
        input8.onClick { keyboardActionHandler(input8) }
        input9.onClick { keyboardActionHandler(input9) }
        inputDot.onClick { keyboardActionHandler(inputDot) }
        inputBack.onClick { keyboardActionHandler(inputBack) }
    }

    /**
     * Set up the selected keyboard
     */
    private fun setUpKeyboard() {
        when (keyboardType) {
            KeyboardType.Normal -> setUpNormalKeyboard()
            // TODO create frequent keyboard type
            KeyboardType.Frequent -> throw UnsupportedOperationException()
            else -> {
                throw IllegalArgumentException("Non supported keyboard type is present")
            }
        }
    }

    /**
     * Set up the current values
     */
    private fun setUpNormalKeyboard() {
        input0.tag = KeyboardData(KeyboardAction.Value, 0.0)
        input1.tag = KeyboardData(KeyboardAction.Value, 1.0)
        input2.tag = KeyboardData(KeyboardAction.Value, 2.0)
        input3.tag = KeyboardData(KeyboardAction.Value, 3.0)
        input4.tag = KeyboardData(KeyboardAction.Value, 4.0)
        input5.tag = KeyboardData(KeyboardAction.Value, 5.0)
        input6.tag = KeyboardData(KeyboardAction.Value, 6.0)
        input7.tag = KeyboardData(KeyboardAction.Value, 7.0)
        input8.tag = KeyboardData(KeyboardAction.Value, 8.0)
        input9.tag = KeyboardData(KeyboardAction.Value, 9.0)
        inputBack.tag = KeyboardData(KeyboardAction.Delete)
        inputDot.tag = KeyboardData(KeyboardAction.Dot)
    }

    /**
     * Handles the keyboard action clicks
     */
    private fun keyboardActionHandler(view: View?) {
        val data = view?.tag as? KeyboardData ?: throw RuntimeException("Developer failed to set up actions properly")

        when (data.actionType) {
            KeyboardAction.Value ->
                onActionListener?.onKeyboardValuePressed(data.value ?: -1.0)
            else -> {
                onActionListener?.onKeyboardActionPressed(data.actionType)
            }
        }
    }

    interface OnKeyboardActionListener {
        fun onKeyboardValuePressed(value: Double)
        fun onKeyboardActionPressed(action: KeyboardAction)
    }
}