package com.pinup.pfm.ui.input.action.date

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import butterknife.Bind
import butterknife.OnClick
import butterknife.bindView
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.DatePickerDialogFragment
import com.pinup.pfm.ui.core.view.TimePickerDialogFragment
import org.jetbrains.anko.support.v4.find
import java.util.*
import javax.inject.Inject

/**
 * Input action date fragment
 */
class InputActionDateFragment : BaseFragment, InputActionDateScreen {

    @Inject lateinit var inputActionDatePresenter: InputActionDatePresenter
    @Inject lateinit var supportFragmentManager: FragmentManager

    val dateText by lazy { find<TextView>(R.id.actionDateTransactionTxt22) }

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_action_date
    }

    override fun initObjects(view: View?) {
        inputActionDatePresenter.bind(this)
        inputActionDatePresenter.initDate()
        inputActionDatePresenter.updateDate()
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        inputActionDatePresenter.unbind()
        super.onDestroyView()
    }

    @OnClick(R.id.actionDateTransactionButton)
    fun onDateChangeClicked() {
        inputActionDatePresenter.changeDate()
    }

    //region Screen interactions
    override fun showDatePicker(calendar: Calendar) {
        DatePickerDialogFragment.newInstance(calendar,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    inputActionDatePresenter.updateSelectedDate(year, monthOfYear, dayOfMonth)
                }).show(fragmentManager, "DatePickerDialogFragment")
    }


    override fun showTimePicker(calendar: Calendar) {
        TimePickerDialogFragment.newInstance(calendar,
                TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
                    inputActionDatePresenter.updateSelectedTime(hourOfDay, minute)
                }).show(fragmentManager, "TimePickerDialogFragment")
    }

    override fun updateTranactionTime(date: Date) {
        dateText.text = date.toLocaleString()
    }

    //endregion
}