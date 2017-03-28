package com.pinup.pfm.ui.charts.adapter.holder

import android.view.View
import com.github.mikephil.charting.charts.BarChart
import com.pinup.pfm.R
import com.pinup.pfm.model.chart.IChartDataItem
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.core.view.viewholder.find

/**
 * Viewholder for charts
 */
class PieChartViewHolder(itemView: View?): BaseViewHolder(itemView) {

    val barChart by lazy { find<BarChart>(R.id.chartPie) }

    fun bindValues(item: IChartDataItem) {

    }
}