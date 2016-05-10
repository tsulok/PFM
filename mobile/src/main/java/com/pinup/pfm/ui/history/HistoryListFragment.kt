package com.pinup.pfm.ui.history

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.transaction.ITransactionHistory
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.BaseListFragment
import com.pinup.pfm.ui.history.adapter.HistoryListAdapter
import javax.inject.Inject

/**
 * Fragment for listing history
 */
class HistoryListFragment : BaseListFragment<ITransactionHistory> {

    @Inject lateinit var historyAdapter: HistoryListAdapter

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun initEventHandlers(view: View?) {
        setOnItemClickListener { view, position -> makeToast("Item pos clicked: $position") }
    }

    override fun getAdapter(): BaseAdapter<ITransactionHistory>? {
        return historyAdapter
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
    }

    fun updateDataset() {
        historyAdapter.updateDataSet()
    }

    fun addNewTransaction(transaction: Transaction) {
        historyAdapter.addTransaction(transaction)
    }

    fun updateTransaction(transaction: Transaction) {
        historyAdapter.updateTransaction(transaction)
    }
}