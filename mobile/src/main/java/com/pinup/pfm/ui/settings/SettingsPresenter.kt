package com.pinup.pfm.ui.settings

import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for Settings
 */
class SettingsPresenter
@Inject constructor(): BasePresenter<SettingsScreen>() {

    fun onMainNavigationButtonClicked() {
        screen?.navigateToMain()
    }
}