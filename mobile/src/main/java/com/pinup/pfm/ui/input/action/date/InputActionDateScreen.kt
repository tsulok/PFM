package com.pinup.pfm.ui.input.action.date

import com.pinup.pfm.common.ui.core.BaseScreen
import java.util.*

/**
 * Screen actions for input action date
 */
interface InputActionDateScreen: BaseScreen {

    fun showDatePicker(calendar: Calendar)
    fun showTimePicker(calendar: Calendar)
    fun updateTranactionTime(date: Date)
}