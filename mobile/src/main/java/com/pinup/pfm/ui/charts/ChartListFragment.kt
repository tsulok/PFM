package com.pinup.pfm.ui.charts

import android.view.View
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.domain.event.TransactionUpdatedEvent
import com.pinup.pfm.model.chart.IChartDataItem
import com.pinup.pfm.ui.charts.adapter.ChartListAdapter
import com.pinup.pfm.ui.charts.model.ChartDataViewModel
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.BaseListFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import kotlinx.android.synthetic.main.fragment_charts.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

        chartsMoveMainBtn.setOnClickListener { presenter.onMainNavigationButtonClicked() }
    }

    override fun isEventBusEnabled(): Boolean = true

    override fun getLayoutId(): Int = R.layout.fragment_charts

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun getAdapter(): BaseAdapter<IChartDataItem>? {
        return adapter
    }

    override fun chartLoaded(barChartItem: ChartDataViewModel) {
        adapter.addItem(barChartItem)
    }

    override fun navigateToMain() {
        (parentFragment as? MainNavigatorFragment)?.navigateToMain()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateCharts(event: TransactionUpdatedEvent) {
        adapter.removeAllItems()
        presenter.loadChartData()
    }
}