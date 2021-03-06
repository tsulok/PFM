package com.pinup.pfm.ui.auth.login

import com.pinup.pfm.common.ui.core.BaseScreen

/**
 * Possible actions on login
 */
interface LoginScreen: BaseScreen {
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

    /**
     * View should indicate the user that the mail field is not valid
     */
    fun onMailNotValid()

    /**
     * View should inidicate that the password field is not valid
     */
    fun onPasswordNotValid()

    /**
     * View should inficate that the login process failed
     */
    fun loginFailed()

    /**
     * View should hide all input error
     */
    fun hideInputErrors()

    /**
     * View should create an alert about the failure of the registration
     */
    fun registrationFailed()

    /**
     * View should show an alert about the invalid email address
     */
    fun forgotPasswordEmailNotValid()

}