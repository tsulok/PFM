package com.pinup.pfm.ui.input.action.date

import com.pinup.pfm.PFMApplication
import com.pinup.pfm.interactor.transaction.CurrentTransactionInteractor
import com.pinup.pfm.ui.core.view.BasePresenter
import java.util.*
import javax.inject.Inject

/**
 * Presenter for input action date
 */
class InputActionDatePresenter @Inject constructor(val currentTransactionInteractor: CurrentTransactionInteractor) : BasePresenter<InputActionDateScreen>() {

    private var selectedDate: Calendar = Calendar.getInstance()

    fun initDate() {
        selectedDate.time = currentTransactionInteractor.transactionDate
    }

    fun updateDate() {
        screen?.updateTranactionTime(selectedDate.time)
    }

    fun changeDate() {
        screen?.showDatePicker(selectedDate)
    }

    fun updateSelectedDate(year: Int, month: Int, day: Int) {
        this.selectedDate.set(year, month, day)
        currentTransactionInteractor.transactionDate = selectedDate.time
        screen?.showTimePicker(selectedDate)
    }

    fun updateSelectedTime(hourOfDay: Int, minute: Int) {
        this.selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
        this.selectedDate.set(Calendar.MINUTE, minute)
        currentTransactionInteractor.transactionDate = selectedDate.time
        screen?.updateTranactionTime(selectedDate.time)
    }
}