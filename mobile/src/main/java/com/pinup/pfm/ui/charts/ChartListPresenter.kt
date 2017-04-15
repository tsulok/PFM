package com.pinup.pfm.ui.charts

import android.content.Context
import com.pinup.pfm.R
import com.pinup.pfm.common.ui.core.BasePresenter
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.domain.provider.IChartDataProvider
import com.pinup.pfm.model.chart.ChartType
import com.pinup.pfm.ui.charts.model.ChartDataViewModel
import javax.inject.Inject

/**
 * Presenter for Settings
 */
class ChartListPresenter @Inject constructor(val chartDataProvider: IChartDataProvider,
                                             @ApplicationContext val context: Context)
    : BasePresenter<ChartListScreen>() {

    fun loadChartData() {
        addBarChart(7)
        addPieChart(7)
        addBarChart(30)
        addPieChart(30)
    }

    private fun addBarChart(dayHistoryCount: Int) {
        val barChartItems = chartDataProvider.provideBarChartData(dayHistoryCount)
        val barItemVm = ChartDataViewModel(context.resources.getString(R.string.chart_bar_title, dayHistoryCount),
                ChartType.Bar, barChartItems,
                context.resources.getString(R.string.chart_bar_legend, dayHistoryCount))
        screen?.chartLoaded(barItemVm)
    }

    private fun addPieChart(dayHistoryCount: Int) {
        val pieChartItems = chartDataProvider.providePieChartData(dayHistoryCount)
        val pieItemVm = ChartDataViewModel(context.resources.getString(R.string.chart_pie_title, dayHistoryCount),
                ChartType.Pie, pieChartItems,
                context.resources.getString(R.string.chart_pie_legend, dayHistoryCount))
        screen?.chartLoaded(pieItemVm)
    }

    fun onMainNavigationButtonClicked() {
        screen?.navigateToMain()
    }
}