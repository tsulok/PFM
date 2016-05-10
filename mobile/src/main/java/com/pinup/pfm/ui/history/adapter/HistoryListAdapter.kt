package com.pinup.pfm.ui.history.adapter

import android.content.Context
import android.view.ViewGroup
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.transaction.ITransactionHistory
import com.pinup.pfm.model.transaction.TransactionHistory
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.history.adapter.viewholder.HistoryViewHolder
import org.jetbrains.anko.layoutInflater
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Adapter for listing histories
 */
class HistoryListAdapter : BaseAdapter<ITransactionHistory> {

    @Inject lateinit var transactionInteractor: TransactionInteractor
    @Inject lateinit var currencyInteractor: CurrencyInteractor

    constructor(context: Context) : super(context) {
        PFMApplication.activityInjector?.inject(this)
        updateDataSet()
    }

    fun updateDataSet() {
        clearAndSetItems(transactionInteractor.listAllTransaction()
                .map { it -> TransactionHistory(it) })
    }

    fun addTransaction(transaction: Transaction) {
        addItem(0, TransactionHistory(transaction))
    }

    fun updateTransaction(transaction: Transaction) {
        val tmpTransactionHistory = TransactionHistory(transaction)
        val itemPosition = getItemPosition(tmpTransactionHistory)
        if (itemPosition != BaseAdapter.INDEX_NOT_FOUND) {
            val storedItem = items[itemPosition]
            storedItem.updateTransaction(transaction)
        } else {
            throw RuntimeException("Transaction is not found in the history list. Developer error!")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        val view = context.layoutInflater.inflate(R.layout.item_history, parent, false)
        val vh = HistoryViewHolder(view)
        prepareItemOnClick(vh)
        return vh
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        val viewHolder = holder as? HistoryViewHolder
        val item: ITransactionHistory? = items[position]

        if (viewHolder == null || item == null) {
            throw RuntimeException("Developer error. Item or viewholder is null")
        }

        viewHolder.nameTextView.text = item.getName()
        viewHolder.timeTextView.text = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM).format(item.getDate())

        val symbol = currencyInteractor.getCurrencySymbol(item.getCurrency())
        viewHolder.currencyTextView.text = " $symbol"

        val numberFormat = currencyInteractor.getCurrencyNumberFormat(item.getCurrency())
        viewHolder.amountTextView.text = numberFormat.format(item.getAmount())
    }
}