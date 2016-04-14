package com.pinup.pfm.ui.history

import android.view.View
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.extensions.makeToast
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
}