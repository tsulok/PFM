package com.pinup.pfm.ui.charts

import android.view.View
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter

/**
 * Fragment for settings
 */
class ChartListFragment : BaseFragment(), ChartListScreen {

    override fun getPresenter(): IBasePresenter? = null
    override fun getScreen(): BaseScreen = this

    override fun getLayoutId(): Int {
        return R.layout.fragment_charts
    }

    override fun initObjects(view: View?) {

    }

    override fun initEventHandlers(view: View?) {

    }

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }
}