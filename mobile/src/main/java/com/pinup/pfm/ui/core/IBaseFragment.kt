package com.pinup.pfm.ui.core

import android.view.View

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