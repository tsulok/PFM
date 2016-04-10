package com.pinup.pfm.ui.settings

import android.view.View
import android.widget.ImageButton
import butterknife.Bind
import com.orhanobut.logger.Logger
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.BaseFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout

/**
 * Fragment for settings
 */
class InputFragment : BaseFragment(), InputScreen {

    @Bind(R.id.slidingPanel) lateinit var slidingPanel: SlidingUpPanelLayout
    @Bind(R.id.favouriteCategoryBtn1) lateinit var favCategoryBtn1: ImageButton
    @Bind(R.id.favouriteCategoryBtn2) lateinit var favCategoryBtn2: ImageButton
    @Bind(R.id.favouriteCategoryBtn3) lateinit var favCategoryBtn3: ImageButton
    @Bind(R.id.favouriteCategoryBtnMore) lateinit var favCategoryBtnMore: ImageButton

    override fun getLayoutId(): Int {
        return R.layout.fragment_input
    }

    override fun initObjects(view: View?) {
        slidingPanel.setDragView(favCategoryBtnMore)
    }

    override fun initEventHandlers(view: View?) {
        slidingPanel.addPanelSlideListener(slidingPanelHandler)
    }

    val slidingPanelHandler = object : SlidingUpPanelLayout.SimplePanelSlideListener() {

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            favCategoryBtnMore.rotation = slideOffset * 180
        }
    }
}