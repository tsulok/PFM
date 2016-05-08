package com.pinup.pfm.ui.input.action.description

import android.view.View
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import javax.inject.Inject

/**
 * Input action map fragment
 */
class InputActionDescriptionFragment : BaseFragment, InputActionDescriptionScreen {

    @Inject lateinit var inputActionDescriptionPresenter: InputActionDescriptionPresenter

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_description
    }

    override fun initObjects(view: View?) {
        inputActionDescriptionPresenter.bind(this)
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        inputActionDescriptionPresenter.unbind()
        super.onDestroyView()
    }
}