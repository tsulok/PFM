package com.pinup.pfm.ui.charts

import com.pinup.pfm.ui.charts.model.ChartDataViewModel
import com.pinup.pfm.ui.core.view.BaseScreen

/**
 * Screen actions for settings
 */
interface ChartListScreen : BaseScreen {
    fun chartLoaded(barChartItem: ChartDataViewModel)
}