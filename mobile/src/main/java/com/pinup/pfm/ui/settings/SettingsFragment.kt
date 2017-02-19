package com.pinup.pfm.ui.settings

import android.view.View
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter

/**
 * Fragment for settings
 */
class SettingsFragment : BaseFragment(), SettingsScreen {

    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }

    // TODO create presenter
    override fun getPresenter(): IBasePresenter? = null
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initObjects(view: View?) {

    }

    override fun initEventHandlers(view: View?) {

    }
}