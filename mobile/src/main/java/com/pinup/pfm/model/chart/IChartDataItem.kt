package com.pinup.pfm.model.chart

import com.github.mikephil.charting.data.Entry
import com.pinup.pfm.R

/**
 * Items for chart
 */
interface IChartDataItem {
    val title: String get
    val chartType: ChartType get
    val entries: List<Entry> get
    val legend: String get
}

enum class ChartType {
    Bar,
    Pie;

    val itemType: Int get() {
        when (this) {
            Bar -> return R.layout.chart_item_bar_layout
            Pie -> return R.layout.chart_item_pie_layout
        }
    }
}