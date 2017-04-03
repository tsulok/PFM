package com.pinup.pfm.ui.category

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.orhanobut.logger.Logger
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.di.component.PFMFragmentComponent
import com.pinup.pfm.extensions.makeToast
import com.pinup.pfm.model.category.ICategoryItem
import com.pinup.pfm.model.database.Category
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.BaseListFragment
import com.pinup.pfm.ui.category.adapter.CategoryListAdapter
import com.pinup.pfm.ui.core.view.BaseScreen
import com.pinup.pfm.ui.core.view.EmptyScreen
import com.pinup.pfm.ui.core.view.IBasePresenter
import javax.inject.Inject

/**
 * Fragment for listing categories
 */
class CategoryListFragment : BaseListFragment<ICategoryItem>(), EmptyScreen {

    companion object {
        @JvmStatic var TAG = CategoryListFragment::class.simpleName
    }

    @Inject lateinit var categoryAdapter: CategoryListAdapter
    @Inject lateinit var presenter: CategoryPresenter
    var onCategoryInteractionListener: OnCategoryInteractionListener? = null

    override fun injectFragment(component: PFMFragmentComponent) {
        component.inject(this)
    }

    override fun getPresenter(): IBasePresenter? = presenter
    override fun getScreen(): BaseScreen = this

    override fun initObjects(view: View?) {
        super.initObjects(view)
        presenter.loadCategories()
    }

    override fun initEventHandlers(view: View?) {
        setOnItemClickListener { view, position ->
            categoryAdapter.removeCurrentItemSelection()
            onCategoryInteractionListener?.onCategorySelected(categoryAdapter.items[position].getCategory())
            categoryAdapter.notifyItemChanged(position)
        }
    }

    override fun getAdapter(): BaseAdapter<ICategoryItem>? {
        return categoryAdapter
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 4)
    }

    /**
     * Interface for category interaction actions
     */
    interface OnCategoryInteractionListener {
        fun onCategorySelected(category: Category)
    }

    fun reloadTransactionCategory() {
        categoryAdapter.notifyDataSetChanged()
    }
}