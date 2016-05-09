package com.pinup.pfm.ui.core.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

/**
 * A custom time picker fragment
 */
class TimePickerDialogFragment : DialogFragment() {

    companion object {

        @JvmStatic val ARG_CALENDAR = "TimePickerCalendar"

        @JvmStatic fun newInstance(calendar: Calendar?, onDateSetListener: TimePickerDialog.OnTimeSetListener): TimePickerDialogFragment {
            val timePickkerFragment = TimePickerDialogFragment()
            val bundle = Bundle()
            if (calendar != null) {
                bundle.putSerializable(ARG_CALENDAR, calendar)
            }
            timePickkerFragment.arguments = bundle
            timePickkerFragment.onTimeSetListener = onDateSetListener
            return timePickkerFragment
        }
    }

    private var onTimeSetListener: TimePickerDialog.OnTimeSetListener? = null
    private lateinit var initialCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null && arguments.containsKey(ARG_CALENDAR)) {
            initialCalendar = arguments.getSerializable(ARG_CALENDAR) as Calendar
        } else {
            initialCalendar = Calendar.getInstance()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val hour = initialCalendar.get(Calendar.HOUR_OF_DAY);
        val minute = initialCalendar.get(Calendar.MINUTE);

        val timePickerDialog = TimePickerDialog(activity, onTimeSetListener, hour, minute, true)
        return timePickerDialog
    }
}