package com.pinup.pfm.ui.input.action.description

import android.view.View
import android.widget.EditText
import android.widget.TextView
import butterknife.Bind
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

/**
 * Input action map fragment
 */
class InputActionDescriptionFragment : BaseFragment(), InputActionDescriptionScreen {

    @Inject lateinit var presenter: InputActionDescriptionPresenter

    val editText by lazy { find<EditText>(R.id.inputActionDescriptionEditTxt22) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_description
    }

    override fun getPresenter(): IBasePresenter? = presenter
    override fun getScreen(): BaseScreen = this

    override fun initObjects(view: View?) {
        presenter.updateDescription()
    }

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onStop() {
        super.onStop()
        presenter.updateDescriptionText(editText.text.toString())
    }

    //region Screen interactions
    override fun updateDescriptionText(description: String) {
        editText.setText(description)
    }
    //endregion
}