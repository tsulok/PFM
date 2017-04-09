package com.pinup.pfm.ui.input.container

import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import butterknife.Bind
import butterknife.OnClick
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.domain.event.transaction.TransactionUpdatedEvent
import com.pinup.pfm.extensions.getDrawableForName
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.model.database.Transaction
import com.pinup.pfm.model.transaction.OnTransactionInteractionListener
import com.pinup.pfm.ui.category.CategoryListFragment
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import com.pinup.pfm.ui.history.HistoryListFragment
import com.pinup.pfm.ui.input.main.InputMainFragment
import com.pinup.pfm.ui.main_navigator.MainNavigatorFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

/**
 * Fragment for input container
 */
class InputContainerFragment : BaseFragment(), InputContainerScreen {

    val categoryListFragment: CategoryListFragment by lazy { CategoryListFragment() }
    val historyListFragment: HistoryListFragment by lazy { HistoryListFragment() }
    val inputMainFragment: InputMainFragment by lazy { InputMainFragment() }
    @Inject lateinit var containerPresenter: InputContainerPresenter

    val slidingPanelTop by lazy { find<SlidingUpPanelLayout>(R.id.slidingPanelTop) }
    val slidingPanelBottom by lazy { find<SlidingUpPanelLayout>(R.id.slidingPanelBottom) }
    val favCategoryBtnMore by lazy { find<ImageButton>(R.id.favouriteCategoryBtnMore) }
    val historySliderView by lazy { find<FrameLayout>(R.id.historySlider) }
    val categorySliderView by lazy { find<LinearLayout>(R.id.categorySlider) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input_container
    }

    override fun getPresenter(): IBasePresenter? = containerPresenter
    override fun getScreen(): BaseScreen = this

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun initObjects(view: View?) {

        // Apply custom draggings to the slider views
        slidingPanelBottom.setDragView(categorySliderView)
        slidingPanelTop.setDragView(historySliderView)

        // Add the slider fragments
        replaceFragment(childFragmentManager, R.id.categoryListContainer,
            categoryListFragment, categoryListFragment.javaClass.canonicalName)

        replaceFragment(childFragmentManager, R.id.historyFrameContainer,
                historyListFragment, historyListFragment.javaClass.canonicalName)

        // Add main input fragment
        replaceFragment(childFragmentManager, R.id.inputMainContainer,
                inputMainFragment, inputMainFragment.javaClass.canonicalName)
    }

    override fun initEventHandlers(view: View?) {
        slidingPanelBottom.addPanelSlideListener(slidingPanelHandler)
        inputMainFragment.onTransactionInteractionListener = transactionInteractionHandler
        historyListFragment.onTransactionInteractionListener = transactionInteractionHandler
        categoryListFragment.onCategoryInteractionListener = categoryInteractionHandler
    }

    val categoryInteractionHandler = object: CategoryListFragment.OnCategoryInteractionListener {
        override fun onCategorySelected(category: Category) {
            containerPresenter.transactionManager.transactionSelectedCategory = category
        }
    }

    /**
     * Handling transaction events
     */
    val transactionInteractionHandler = object : OnTransactionInteractionListener {
        override fun onTransactionAdded(transaction: Transaction) {
            categoryListFragment.reloadTransactionCategory()
            historyListFragment.addNewTransaction(transaction)
            eventBus.post(TransactionUpdatedEvent())
        }

        override fun onTransactionEdited(transaction: Transaction) {
            categoryListFragment.reloadTransactionCategory()
            historyListFragment.updateTransaction(transaction)
            eventBus.post(TransactionUpdatedEvent())
        }

        override fun onTransactionDeleted(transaction: Transaction) {
            categoryListFragment.reloadTransactionCategory()
            eventBus.post(TransactionUpdatedEvent())
        }

        override fun onTransactionOpened(transaction: Transaction) {
            listHistoryClicked() // Click on the opened history list to be closed
            inputMainFragment.reloadTransaction()
            categoryListFragment.reloadTransactionCategory()
        }

        override fun onNewTransaction() {
            inputMainFragment.reloadTransaction()
            categoryListFragment.reloadTransactionCategory()
        }
    }

    /**
     * Sliding panel handler for appropriate image
     */
    val slidingPanelHandler = object : SlidingUpPanelLayout.SimplePanelSlideListener() {

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            favCategoryBtnMore.rotation = slideOffset * 180
        }
    }

    @OnClick(R.id.favouriteCategoryBtnMore)
    fun moreCategoryClicked() {

        when (slidingPanelBottom.panelState) {
            SlidingUpPanelLayout.PanelState.COLLAPSED -> slidingPanelBottom.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            SlidingUpPanelLayout.PanelState.EXPANDED -> slidingPanelBottom.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            else -> Logger.d("Invalid state")
        }
    }

    @OnClick(R.id.listHistoryButton)
    fun listHistoryClicked() {

        when (slidingPanelTop.panelState) {
            SlidingUpPanelLayout.PanelState.COLLAPSED -> slidingPanelTop.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            SlidingUpPanelLayout.PanelState.EXPANDED -> slidingPanelTop.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            else -> Logger.d("Invalid state")
        }
    }

    @OnClick(R.id.chartButton)
    fun openChartButtonClicked() {
        containerPresenter.navigateToCharts()
    }

    @OnClick(R.id.profileButton)
    fun onProfileButtonClicked() {
        containerPresenter.navigateToSettings()
    }

    //region Screen actions
    override fun navigateToCharts() {
        (parentFragment as? MainNavigatorFragment)?.navigateToCharts()
    }

    override fun navigateToSettings() {
        (parentFragment as? MainNavigatorFragment)?.navigateToSettings()
    }

    override fun openTransactionHistory() {

    }

    //endregion
}