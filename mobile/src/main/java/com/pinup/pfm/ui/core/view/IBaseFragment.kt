package com.pinup.pfm.ui.core.view

import android.view.View
import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.common.ui.core.IBasePresenter

/**
 * Must have methods should be called during fragment initialization.
 */
interface IBaseFragment {

    /**
     * The layout of the fragment
     *
     * @return The associated layout id
     */
    fun getLayoutId(): Int

    /**
     * Returns the presenter of the fragment. Used for lifecycle events
     *
     * @return The presenter of the fragment
     */
    fun getPresenter(): IBasePresenter?

    /**
     * Returns the screen of this fragment
     */
    fun getScreen(): BaseScreen

    /**
     * Other (non view typed) fields (such as adapters) should be initialized here.
     *
     * @param view Root view (which will be returned in onCreateView)
     */
    fun initObjects(view: View?)

    /**
     * Event handlers of view typed fields (such as onClickListeners) should be initialized here.
     *
     * @param view Root view (which will be returned in onCreateView)
     */
    fun initEventHandlers(view: View?)
}