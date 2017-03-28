package com.pinup.pfm.ui.charts.adapter.holder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.pinup.pfm.R
import com.pinup.pfm.model.chart.IChartDataItem
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.core.view.viewholder.find

/**
 * Viewholder for charts
 */
class PieChartViewHolder(itemView: View?): BaseViewHolder(itemView) {

    val pieChart by lazy { find<PieChart>(R.id.chartPie) }
    val titleTxt by lazy { find<TextView>(R.id.chartTitleTxt) }

    init {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = false
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        val legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.xEntrySpace = 7f
        legend.yEntrySpace = 7f
        legend.yOffset = 0f
    }

    fun bindValues(item: IChartDataItem) {

    }
}