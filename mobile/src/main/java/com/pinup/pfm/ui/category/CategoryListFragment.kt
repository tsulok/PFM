package com.pinup.pfm.ui.category

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.model.category.ICategoryItem
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.BaseListFragment
import com.pinup.pfm.ui.category.adapter.CategoryListAdapter
import javax.inject.Inject

/**
 * Fragment for listing categories
 */
class CategoryListFragment : BaseListFragment<ICategoryItem> {

    companion object {
        @JvmStatic var TAG = CategoryListFragment::class.simpleName
    }

    @Inject lateinit var categoryAdapter: CategoryListAdapter

    constructor() : super() {
        Logger.d("Category List constructor")
        PFMApplication.activityInjector?.inject(this)
    }

    override fun initEventHandlers(view: View?) {
        setOnItemClickListener { view, position -> makeToast("Item clicked: $position") }
    }

    override fun getAdapter(): BaseAdapter<ICategoryItem>? {
        return categoryAdapter
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 4)
    }
}