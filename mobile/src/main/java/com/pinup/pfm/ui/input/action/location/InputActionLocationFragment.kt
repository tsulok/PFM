package com.pinup.pfm.ui.input.action.location

import android.view.View
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import javax.inject.Inject

/**
 * Input action location fragment
 */
class InputActionLocationFragment : BaseFragment, InputActionLocationScreen {

    @Inject lateinit var inputActionLocationPresenter: InputActionLocationPresenter

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_location
    }

    override fun initObjects(view: View?) {
        inputActionLocationPresenter.bind(this)
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        inputActionLocationPresenter.unbind()
        super.onDestroyView()
    }
}