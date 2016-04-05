package com.pinup.pfm.ui.settings

import android.view.View
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment

/**
 * Fragment for settings
 */
class SettingsFragment : BaseFragment(), SettingsScreen {

    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }

    override fun initObjects(view: View?) {

    }

    override fun initEventHandlers(view: View?) {

    }
}