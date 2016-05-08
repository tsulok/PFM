package com.pinup.pfm.ui.input.action.date

import android.view.View
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import javax.inject.Inject

/**
 * Input action date fragment
 */
class InputActionDateFragment : BaseFragment, InputActionDateScreen {

    @Inject lateinit var inputActionDatePresenter: InputActionDatePresenter

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_date
    }

    override fun initObjects(view: View?) {
        inputActionDatePresenter.bind(this)
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        inputActionDatePresenter.unbind()
        super.onDestroyView()
    }
}