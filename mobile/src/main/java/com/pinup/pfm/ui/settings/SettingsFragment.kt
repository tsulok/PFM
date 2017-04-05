package com.pinup.pfm.ui.settings

import android.view.View
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import kotlinx.android.synthetic.main.settings_fragment.*
import javax.inject.Inject

/**
 * Fragment for settings
 */
class SettingsFragment : BaseFragment(), SettingsScreen {

    @Inject lateinit var presenter: SettingsPresenter

    override fun getLayoutId(): Int {
        return R.layout.settings_fragment
    }

    // TODO create presenter
    override fun getPresenter(): IBasePresenter? = presenter
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initObjects(view: View?) {

    }

    override fun initEventHandlers(view: View?) {
        settingsMoveMainBtn.setOnClickListener { presenter.onMainNavigationButtonClicked() }
    }

    override fun navigateToMain() {
        (parentFragment as? MainNavigatorFragment)?.navigateToMain()
    }
}