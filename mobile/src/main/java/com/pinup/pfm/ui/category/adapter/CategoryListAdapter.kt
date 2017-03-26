package com.pinup.pfm.ui.category.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pinup.pfm.R
import com.pinup.pfm.di.qualifiers.ActivityContext
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.extensions.getDrawableForName
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.model.category.CategoryItem
import com.pinup.pfm.model.category.ICategoryItem
import com.pinup.pfm.ui.category.adapter.viewholder.CategoryViewHolder
import com.pinup.pfm.ui.core.adapter.BaseAdapter
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import org.jetbrains.anko.image
import javax.inject.Inject

/**
 * Adapter for listing categories
 */
class CategoryListAdapter @Inject constructor(@ActivityContext context: Context,
                                              val categoryInteractor: ICategoryInteractor,
                                              val transactionManager: ITransactionManager)
    : BaseAdapter<ICategoryItem>(context) {

    init {
        categoryInteractor.listAllSelectableCategories().forEach { it -> addItem(CategoryItem(it)) }
        listAll()
    }

    fun listAll() {
        categoryInteractor.listAllSelectableCategories().forEach { it -> addItem(CategoryItem(it)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        val viewHolder = CategoryViewHolder(view)
        prepareItemOnClick(viewHolder)
        return viewHolder
    }

    fun removeCurrentItemSelection() {
        val selectedCategory = transactionManager.transactionSelectedCategory
        transactionManager.transactionSelectedCategory = null
        if (selectedCategory != null) {
            val position = items.indexOf(CategoryItem(selectedCategory))
            if (position >= 0 && position < items.size) {
                notifyItemChanged(position)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        val viewHolder = holder as? CategoryViewHolder
        val item: ICategoryItem? = items[position]

        if (viewHolder == null || item == null) {
            throw RuntimeException("Developer error. Item or viewholder is null")
        }

        val isItemSelected = item.getCategoryId().equals(transactionManager.transactionSelectedCategory?.id)

        viewHolder.itemNameText.text = item.getName()

        val categoryDrawable = context.getDrawableForName(item.getIconUri())
        categoryDrawable?.mutate()

        var iconColor: Int = context.resources.getColor(R.color.input_category_icon_unselected)
        if (isItemSelected) {
            iconColor = context.resources.getColor(R.color.input_category_icon_selected)
        }
        categoryDrawable?.setColorFilter(iconColor, PorterDuff.Mode.SRC_IN)

        viewHolder.itemImage.image = categoryDrawable
        viewHolder.itemImage.isSelected = isItemSelected
    }
}