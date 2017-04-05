package com.pinup.pfm.ui.settings

import com.pinup.pfm.ui.core.view.BaseScreen
import java.util.*

/**
 * Screen actions for settings
 */
interface SettingsScreen : BaseScreen {

    /**
     * View should navigate to main
     */
    fun navigateToMain()

    /**
     * View should ask user to confirm onLogoutClicked intent
     */
    fun askUserConfirmLogout()

    /**
     * View should onLogoutClicked the user
     */
    fun logoutUser()

    /**
     * View should show a loading progress
     */
    fun loadingStarted()

    /**
     * View should hide the loading progress
     */
    fun loadingFinished()

    /**
     * View should update last sync time
     */
    fun updateLastSyncTime(date: Date?)

    /**
     * View should be populated with the following items
     */
    fun initView(settingsViewModel: ISettingsViewModel)

    /**
     * View should show an error about the update
     */
    fun updateFailed()
}