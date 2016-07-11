package com.pinup.pfm.ui.core.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import butterknife.Bind
import com.orhanobut.logger.Logger
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import org.jetbrains.anko.support.v4.find

/**
 * Base listFragment class
 */
abstract class BaseListFragment<T> : BaseFragment {

    val recyclerView by lazy { find<RecyclerView>(R.id.recyclerList) }
    val listOverlayContainer by lazy { find<FrameLayout>(R.id.listOverlayContainer) }

    constructor() : super() {
        Logger.d("Base list constructor")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.d("Base List onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun getAdapter(): BaseAdapter<T>?

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    override fun initObjects(view: View?) {
        if (getAdapter() == null) {
            throw RuntimeException("RecyclerView is not properly initialized")
        }

        if(recyclerView.adapter == null) {
            recyclerView.layoutManager = getLayoutManager();
            recyclerView.adapter = getAdapter();
        }
    }

    override fun initEventHandlers(view: View?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
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

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(recyclerView.context)
    }

    fun setOnItemClickListener(handler: (view: View, position: Int) -> Unit) {
        getAdapter()?.onItemClickListener = object : BaseViewHolder.OnItemClickListener {
            override fun listItemClicked(view: View, position: Int) = handler(view, position)
        }
    }
}