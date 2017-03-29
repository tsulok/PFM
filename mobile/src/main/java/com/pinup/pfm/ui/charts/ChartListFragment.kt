package com.pinup.pfm.ui.charts

import android.view.View
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.model.chart.IChartDataItem
import com.pinup.pfm.ui.charts.adapter.ChartListAdapter
import com.pinup.pfm.ui.charts.model.ChartDataViewModel
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.BaseListFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import javax.inject.Inject

/**
 * Fragment for settings
 */
class ChartListFragment : BaseListFragment<IChartDataItem>(), ChartListScreen {

    @Inject lateinit var presenter: ChartListPresenter
    @Inject lateinit var adapter: ChartListAdapter

    override fun getPresenter(): IBasePresenter? = presenter
    override fun getScreen(): BaseScreen = this

    override fun initObjects(view: View?) {
        super.initObjects(view)
        adapter.removeAllItems()
        presenter.loadChartData()
    }

    override fun initEventHandlers(view: View?) {
        super.initEventHandlers(view)
    }

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun getAdapter(): BaseAdapter<IChartDataItem>? {
        return adapter
    }

    override fun chartLoaded(barChartItem: ChartDataViewModel) {
        adapter.addItem(barChartItem)
    }
}