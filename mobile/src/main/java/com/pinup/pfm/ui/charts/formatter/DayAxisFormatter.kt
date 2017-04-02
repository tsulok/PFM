package com.pinup.pfm.ui.charts.formatter

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Day axis value formatter
 */
class DayAxisFormatter: IAxisValueFormatter {

    val calendar: Calendar = Calendar.getInstance()
    val dateFormatter: DateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        calendar.set(Calendar.DAY_OF_YEAR, value.toInt())
        return dateFormatter.format(calendar.time)
    }
}