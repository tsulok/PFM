package com.pinup.pfm.ui.category.adapter.viewholder

import android.view.View
import android.widget.Button
import butterknife.Bind
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder

/**
 * View holder for categories
 */
class CategoryViewHolder : BaseViewHolder {

    @Bind(R.id.categoryItemBtn) lateinit var itemButton: Button

    constructor(itemView: View?) : super(itemView) {
        clickableView = itemButton
    }
}