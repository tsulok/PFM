package com.pinup.pfm.ui.charts.adapter

import android.content.Context
import android.view.ViewGroup
import com.pinup.pfm.R
import com.pinup.pfm.di.qualifiers.ActivityContext
import com.pinup.pfm.interactor.transaction.TransactionInteractor
import com.pinup.pfm.interactor.utils.CurrencyInteractor
import com.pinup.pfm.model.chart.ChartType
import com.pinup.pfm.model.chart.IChartDataItem
import com.pinup.pfm.ui.charts.adapter.holder.BarChartViewHolder
import com.pinup.pfm.ui.charts.adapter.holder.PieChartViewHolder
import com.pinup.pfm.ui.charts.model.ChartDataViewModel
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.history.adapter.viewholder.HistoryViewHolder
import org.jetbrains.anko.layoutInflater
import javax.inject.Inject

/**
 * Adapter for listing charts
 */
class ChartListAdapter @Inject constructor(@ActivityContext context: Context,
                                             val transactionInteractor: TransactionInteractor,
                                             val currencyInteractor: CurrencyInteractor)
    : BaseAdapter<IChartDataItem>(context) {

    // Viewtype identifies the layout itself
    override fun getItemViewType(position: Int): Int {
        return items[position].chartType.itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val view = context.layoutInflater.inflate(viewType, parent, false)

        var vh: BaseViewHolder = BaseViewHolder(view)
        when (viewType) {
            ChartType.Bar.itemType -> vh = BarChartViewHolder(view)
            ChartType.Pie.itemType -> vh = PieChartViewHolder(view)
            else -> IllegalArgumentException("Not supported chart model")
        }

        prepareItemOnClick(vh)
        return vh
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        when (holder) {
            is BarChartViewHolder -> holder.bindValues(items[position])
            is PieChartViewHolder -> holder.bindValues(items[position])
        }
    }
}