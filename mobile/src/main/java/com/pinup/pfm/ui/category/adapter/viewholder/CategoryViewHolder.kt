package com.pinup.pfm.ui.category.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder

/**
 * View holder for categories
 */
class CategoryViewHolder : BaseViewHolder {

    @Bind(R.id.categoryItemImageButton) lateinit var itemImage: ImageButton
    @Bind(R.id.categoryItemName) lateinit var itemNameText: TextView

    constructor(itemView: View?) : super(itemView) {
        clickableView = itemImage
    }
}