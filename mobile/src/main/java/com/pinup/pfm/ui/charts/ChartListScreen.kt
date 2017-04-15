package com.pinup.pfm.ui.charts

import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.ui.charts.model.ChartDataViewModel

/**
 * Screen actions for settings
 */
interface ChartListScreen: BaseScreen {
    fun chartLoaded(barChartItem: ChartDataViewModel)

    /**
     * View should navigate to main
     */
    fun navigateToMain()
}