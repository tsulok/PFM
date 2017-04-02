package com.pinup.pfm.ui.charts.model

import com.github.mikephil.charting.data.Entry
import com.pinup.pfm.model.chart.ChartType
import com.pinup.pfm.model.chart.IChartDataItem

/**
 * Viewmodel for chart data
 */
data class ChartDataViewModel constructor(override val title: String,
                                          override val chartType: ChartType,
                                          override val entries: List<Entry>,
                                          override val legend: String) : IChartDataItem