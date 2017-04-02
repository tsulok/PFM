package com.pinup.pfm.ui.history

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.transaction.ITransactionHistory
import com.pinup.pfm.model.transaction.OnTransactionInteractionListener
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.BaseListFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.history.adapter.HistoryListAdapter
import javax.inject.Inject

/**
 * Fragment for listing history
 */
class HistoryListFragment
    : BaseListFragment<ITransactionHistory>(), HistoryScreen {

    @Inject lateinit var historyAdapter: HistoryListAdapter
    @Inject lateinit var historyPresenter: HistoryPresenter
    @Inject lateinit var transactionManager: ITransactionManager

    var onTransactionInteractionListener: OnTransactionInteractionListener? = null

    override fun getPresenter(): IBasePresenter? = historyPresenter
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initEventHandlers(view: View?) {
        setOnItemClickListener { view, position ->  historyPresenter.loadSavedTransaction(historyAdapter.items[position]) }
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

    override fun loadSavedTransaction(transaction: Transaction) {
        transactionManager.loadSavedTransaction(transaction)
        onTransactionInteractionListener?.onTransactionOpened(transaction)
    }
}