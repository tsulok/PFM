package com.pinup.pfm.ui.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.extensions.getDrawableForName
import com.pinup.pfm.interactor.category.CategoryInteractor
import com.pinup.pfm.model.category.CategoryItem
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.category.adapter.viewholder.CategoryViewHolder
import com.pinup.pfm.model.category.ICategoryItem
import javax.inject.Inject

/**
 * Adapter for listing categories
 */
class CategoryListAdapter : BaseAdapter<ICategoryItem> {

    @Inject lateinit var categoryInteractor: CategoryInteractor

    constructor(context: Context) : super(context) {
        PFMApplication.activityInjector?.inject(this)

        for (category in categoryInteractor.listAllSelectableCategories()) {
            addItem(CategoryItem(category.name, category.order, category.imageUri))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        val viewHolder = CategoryViewHolder(view)
        prepareItemOnClick(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        val viewHolder = holder as? CategoryViewHolder
        val item: ICategoryItem? = items[position]

        if (viewHolder == null || item == null) {
            throw RuntimeException("Developer error. Item or viewholder is null")
        }

        viewHolder.itemButton.text = item.getName()
        viewHolder.itemButton.setCompoundDrawablesWithIntrinsicBounds(null, context.getDrawableForName(item.getIconUri()), null, null)
    }
}