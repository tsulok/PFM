package com.pinup.pfm.ui.input.action.description

import android.view.View
import android.widget.EditText
import butterknife.Bind
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import javax.inject.Inject

/**
 * Input action map fragment
 */
class InputActionDescriptionFragment : BaseFragment, InputActionDescriptionScreen {

    @Inject lateinit var inputActionDescriptionPresenter: InputActionDescriptionPresenter

    @Bind(R.id.inputActionDescriptionEditTxt22) lateinit var editText: EditText

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_description
    }

    override fun initObjects(view: View?) {
        inputActionDescriptionPresenter.bind(this)
        inputActionDescriptionPresenter.updateDescription()
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        inputActionDescriptionPresenter.unbind()
        super.onDestroyView()
    }

    //region Screen interactions
    override fun updateDescriptionText(description: String) {
        editText.setText(description)
    }
    //endregion
}