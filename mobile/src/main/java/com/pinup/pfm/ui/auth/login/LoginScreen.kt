package com.pinup.pfm.ui.auth.login

import com.pinup.pfm.ui.core.view.BaseScreen

/**
 * Possible actions on login
 */
interface LoginScreen : BaseScreen {
    /**
     * View should show a loader
     */
    fun loadingStarted()

    /**
     * View should hide loader
     */
    fun loadingFinished()

    /**
     * View shoud navigate to main screen
     */
    fun moveToMain()

}