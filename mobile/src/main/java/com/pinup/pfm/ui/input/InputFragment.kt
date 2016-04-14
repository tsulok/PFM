package com.pinup.pfm.ui.settings

import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import butterknife.Bind
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.replaceFragment
import com.pinup.pfm.ui.core.view.BaseFragment
import com.pinup.pfm.ui.input.category.CategoryListFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import javax.inject.Inject

/**
 * Fragment for settings
 */
class InputFragment : BaseFragment, InputScreen {

    @Inject lateinit var categoryListFragment: CategoryListFragment

    @Bind(R.id.slidingPanel) lateinit var slidingPanel: SlidingUpPanelLayout
    @Bind(R.id.favouriteCategoryBtn1) lateinit var favCategoryBtn1: Button
    @Bind(R.id.favouriteCategoryBtn2) lateinit var favCategoryBtn2: Button
    @Bind(R.id.favouriteCategoryBtn3) lateinit var favCategoryBtn3: Button
    @Bind(R.id.favouriteCategoryBtnMore) lateinit var favCategoryBtnMore: ImageButton

    constructor() : super() {
        PFMApplication.activityInjector?.inject(this)
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_input
    }

    override fun initObjects(view: View?) {
        slidingPanel.setDragView(favCategoryBtnMore)

        slidingPanel.post( {
            replaceFragment(childFragmentManager, R.id.categoryListContainer,
                categoryListFragment, categoryListFragment.javaClass.canonicalName)
        } )
    }

    override fun initEventHandlers(view: View?) {
        slidingPanel.addPanelSlideListener(slidingPanelHandler)
    }

    /**
     * Sliding panel handler for appropriate image
     */
    val slidingPanelHandler = object : SlidingUpPanelLayout.SimplePanelSlideListener() {

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            favCategoryBtnMore.rotation = slideOffset * 180
        }
    }
}