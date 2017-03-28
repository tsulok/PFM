package com.pinup.pfm.ui.charts

import android.content.Context
import com.github.mikephil.charting.data.ChartData
import com.pinup.pfm.R
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.domain.provider.IChartDataProvider
import com.pinup.pfm.model.chart.ChartType
import com.pinup.pfm.ui.charts.model.ChartDataViewModel
import com.pinup.pfm.ui.core.view.BasePresenter
import javax.inject.Inject

/**
 * Presenter for Settings
 */
class ChartListPresenter @Inject constructor(val chartDataProvider: IChartDataProvider,
                                             @ApplicationContext val context: Context)
    : BasePresenter<ChartListScreen>() {

    fun loadChartData() {
        val barChartItems = chartDataProvider.provideBarChartData()
        val barItemVm = ChartDataViewModel(context.getString(R.string.chart_bar_title),
                ChartType.Bar, barChartItems,
                context.getString(R.string.chart_bar_legend))
        screen?.chartLoaded(barItemVm)

        var pieChartItems = chartDataProvider.providePieChartData()
        val pieItemVm = ChartDataViewModel(context.getString(R.string.chart_pie_title),
                ChartType.Pie, pieChartItems,
                context.getString(R.string.chart_pie_legend))
        screen?.chartLoaded(pieItemVm)
    }
}