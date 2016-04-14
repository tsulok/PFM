package com.pinup.pfm.ui.history.adapter

import android.content.Context
import android.view.ViewGroup
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.model.transaction.ITransactionHistory
import com.pinup.pfm.model.transaction.TransactionHistory
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.history.adapter.viewholder.HistoryViewHolder
import org.jetbrains.anko.layoutInflater
import java.util.*
import javax.inject.Inject

/**
 * Adapter for listing histories
 */
class HistoryListAdapter : BaseAdapter<ITransactionHistory> {

    @Inject lateinit var transactionInteractor: TransactionInteractor

    constructor(context: Context) : super(context) {
        PFMApplication.activityInjector?.inject(this)

        // TODO read read values from database
        for (i in 1..20) {
            addItem(TransactionHistory("$i", Date(2016, 3, i), i * 124.0, "$"))
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
        viewHolder.timeTextView.text = item.getDate().toLocaleString()
        viewHolder.amountTextView.text = item.getAmount().toString()
        viewHolder.currencyTextView.text = item.getCurrency()
    }
}