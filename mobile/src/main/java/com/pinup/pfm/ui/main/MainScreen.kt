package com.pinup.pfm.ui.main

import com.pinup.pfm.common.ui.core.BaseScreen
import java.util.*

/**
 * View actions on main
 */

interface MainScreen: BaseScreen {

    /**
     * View should load it's main navigation
     */
    fun loadMainNavigation()

    /**
     * Loading indicator should show to the user
     */
    fun loadingStarted()

    /**
     * Loading indicator should hide
     */
    fun loadingFinished()

    /**
     * View should show an error with initial loading failed error
     */
    fun loadInitialDataFailedToLoad()

    /**
     * View should create a toast with message
     */
    fun syncFailedButHasInitialData()

    /**
     * View should show default currency chooser
     */
    fun showDefaultCurrencyChooser(currencies: List<Currency>)
}
