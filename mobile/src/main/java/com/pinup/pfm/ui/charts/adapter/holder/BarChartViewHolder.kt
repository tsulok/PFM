package com.pinup.pfm.ui.charts.adapter.holder

import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.pinup.pfm.R
import com.pinup.pfm.model.chart.IChartDataItem
import com.pinup.pfm.ui.charts.formatter.DayAxisFormatter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.core.view.viewholder.find

/**
 * Viewholder for charts
 */
class BarChartViewHolder(itemView: View?): BaseViewHolder(itemView) {

    val barChart by lazy { find<BarChart>(R.id.chartBar) }
    val titleTxt by lazy { find<TextView>(R.id.chartTitleTxt) }

    init {
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.disableScroll()
        barChart.setPinchZoom(false)
        barChart.isDoubleTapToZoomEnabled = false

        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.setDrawGridLines(false)
        barChart.xAxis.granularity = 1f
        barChart.xAxis.valueFormatter = DayAxisFormatter()
        barChart.xAxis.labelRotationAngle = -65f

        barChart.axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisLeft.setLabelCount(8, false)
        barChart.axisLeft.spaceTop = 15f
    }

    fun bindValues(item: IChartDataItem) {
        titleTxt.text = item.title

        val entries = item.entries.map { BarEntry(it.x, it.y) }
        val dataSet = BarDataSet(entries, item.legend)
        dataSet.setDrawIcons(false)

        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)

        val dataSets: MutableList<IBarDataSet> = ArrayList()
        dataSets.add(dataSet)

        val data = BarData(dataSets)
        data.setValueTextSize(10f)
        data.barWidth = 0.9f

        barChart.data = data
    }
}