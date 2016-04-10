package com.pinup.pfm.ui.core.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import butterknife.Bind
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder

/**
 * Base listFragment class
 */
abstract class BaseListFragment<T> : BaseFragment() {

    @Bind(R.id.recyclerList) lateinit var recyclerView: RecyclerView;
    @Bind(R.id.listOverlayContainer) lateinit var listOverlayContainer: FrameLayout

    var onItemClickListener: BaseViewHolder.OnItemClickListener? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    abstract fun getAdapter(): BaseAdapter<T>?

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun initObjects(view: View?) {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(recyclerView.context)
        }

        if (getAdapter() == null) {
            throw RuntimeException("RecyclerView is not properly initialized")
        }

        if(recyclerView.adapter == null) {
            recyclerView.layoutManager = layoutManager;
            recyclerView.adapter = getAdapter();
        }
    }

    override fun initEventHandlers(view: View?) {
        if (onItemClickListener != null) {
            getAdapter()?.onItemClickListener = onItemClickListener
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        layoutManager = null
    }

    fun addOverlayView(view: View) {
        listOverlayContainer.addView(view)
    }

    fun hideOverlayView() {
        listOverlayContainer.visibility = View.GONE
    }

    fun showOverlayView() {
        listOverlayContainer.visibility = View.VISIBLE
    }

    fun updateOverlayView() {
        if (getAdapter() == null || getAdapter()?.itemCount == 0) {
            showOverlayView()
        } else {
            hideOverlayView()
        }
    }
}