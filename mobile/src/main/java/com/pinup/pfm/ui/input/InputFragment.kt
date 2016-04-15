package com.pinup.pfm.ui.input

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import butterknife.Bind
import butterknife.ButterKnife
import butterknife.OnClick
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.ui.category.CategoryListFragment
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.history.HistoryListFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import javax.inject.Inject

/**
 * Fragment for settings
 */
class InputFragment : BaseFragment, InputScreen {

    @Inject lateinit var categoryListFragment: CategoryListFragment
    @Inject lateinit var historyListFragment: HistoryListFragment

    @Bind(R.id.slidingPanelTop) lateinit var slidingPanelTop: SlidingUpPanelLayout
    @Bind(R.id.slidingPanelBottom) lateinit var slidingPanelBottom: SlidingUpPanelLayout
    @Bind(R.id.favouriteCategoryBtn1) lateinit var favCategoryBtn1: Button
    @Bind(R.id.favouriteCategoryBtn2) lateinit var favCategoryBtn2: Button
    @Bind(R.id.favouriteCategoryBtn3) lateinit var favCategoryBtn3: Button
    @Bind(R.id.favouriteCategoryBtnMore) lateinit var favCategoryBtnMore: ImageButton
    @Bind(R.id.historySlider) lateinit var historySliderView: LinearLayout
    @Bind(R.id.categorySlider) lateinit var categorySliderView: LinearLayout

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_input
    }

    override fun initObjects(view: View?) {

        // Included sublayouts must be bound individually
        val inputMainView = view?.findViewById(R.id.inputMainLayout)
        if (inputMainView != null) {
            ButterKnife.bind(this, inputMainView)
        }

        val inputKeyboardView = view?.findViewById(R.id.inputKeyboardLayout)
        if (inputKeyboardView != null) {
            ButterKnife.bind(this, inputKeyboardView)
        }

        val inputKeyboardActionView = view?.findViewById(R.id.inputKeyboardActionLayout)
        if (inputKeyboardActionView != null) {
            ButterKnife.bind(this, inputKeyboardActionView)
        }

        // Apply custom draggings to the slider views
        slidingPanelBottom.setDragView(categorySliderView)
        slidingPanelTop.setDragView(historySliderView)

        // Add the slider fragments
        replaceFragment(childFragmentManager, R.id.categoryListContainer,
            categoryListFragment, categoryListFragment.javaClass.canonicalName)

        replaceFragment(childFragmentManager, R.id.historyFrameContainer,
                historyListFragment, historyListFragment.javaClass.canonicalName)
    }

    override fun initEventHandlers(view: View?) {
        slidingPanelBottom.addPanelSlideListener(slidingPanelHandler)
    }

    /**
     * Sliding panel handler for appropriate image
     */
    val slidingPanelHandler = object : SlidingUpPanelLayout.SimplePanelSlideListener() {

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            favCategoryBtnMore.rotation = slideOffset * 180
        }
    }

    @OnClick(R.id.favouriteCategoryBtn1)
    fun favourite1CategoryClicked() {
        makeToast("1 clicked")
    }

    @OnClick(R.id.favouriteCategoryBtn2)
    fun favourite2CategoryClicked() {
        makeToast("2 clicked")
    }

    @OnClick(R.id.favouriteCategoryBtn3)
    fun favourite3CategoryClicked() {
        makeToast("3 clicked")
    }

    @OnClick(R.id.favouriteCategoryBtnMore)
    fun moreCategoryClicked() {

        when (slidingPanelBottom.panelState) {
            SlidingUpPanelLayout.PanelState.COLLAPSED -> slidingPanelBottom.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            SlidingUpPanelLayout.PanelState.EXPANDED -> slidingPanelBottom.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            else -> Logger.d("Invalid state")
        }
    }

    @OnClick(R.id.input1)
    fun onPhotoClicked() {
        makeToast("1 clicked")
    }

    @OnClick(R.id.inputActionLocation)
    fun onLocationClicked() {
        makeToast("Location clicked")
    }
}