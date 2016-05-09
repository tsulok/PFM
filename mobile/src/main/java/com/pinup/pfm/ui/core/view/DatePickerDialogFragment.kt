package com.pinup.pfm.ui.core.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

/**
 * A custom date picker fragment
 */
class DatePickerDialogFragment : DialogFragment() {

    companion object {

        @JvmStatic val ARG_CALENDAR = "DatePickerCalendar"

        @JvmStatic fun newInstance(calendar: Calendar?, onDateSetListener: DatePickerDialog.OnDateSetListener): DatePickerDialogFragment {
            val datePickerFragment = DatePickerDialogFragment()
            val bundle = Bundle()
            if (calendar != null) {
                bundle.putSerializable(ARG_CALENDAR, calendar)
            }
            datePickerFragment.arguments = bundle
            datePickerFragment.onDateSetListener = onDateSetListener
            return datePickerFragment
        }
    }

    private var onDateSetListener: DatePickerDialog.OnDateSetListener? = null
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

        val year = initialCalendar.get(Calendar.YEAR);
        val month = initialCalendar.get(Calendar.MONTH);
        val day = initialCalendar.get(Calendar.DAY_OF_MONTH);

        val datePickerDialog = DatePickerDialog(getActivity(), onDateSetListener, year, month, day)
        return datePickerDialog
    }
}