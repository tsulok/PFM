package com.pinup.pfm.ui.charts.adapter.holder

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
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
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)
        pieChart.holeRadius = 50f
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(0)
        pieChart.isRotationEnabled = false

        itemView?.let {
            pieChart.setEntryLabelTextSize(13f)
        }
        pieChart.setEntryLabelColor(Color.BLACK)
    }

    fun bindValues(item: IChartDataItem) {
        titleTxt.text = item.title
        pieChart.data = createChartData(item)
        pieChart.invalidate()
    }

    private fun createChartData(item: IChartDataItem): PieData {
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        val entries = item.entries as List<PieEntry>
        val dataSet = PieDataSet(entries, item.legend)

        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        itemView?.let {
            data.setValueTextSize(11f)
        }
        data.setValueTextColor(Color.BLACK)

        return data
    }
}