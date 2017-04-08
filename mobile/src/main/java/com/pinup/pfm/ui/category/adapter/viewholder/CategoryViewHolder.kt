package com.pinup.pfm.ui.category.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.core.view.viewholder.find

/**
 * View holder for categories
 */
class CategoryViewHolder(itemView: View?) : BaseViewHolder(itemView) {

    val itemImageRoot by lazy { find<View>(R.id.categoryItemImageButtonRoot) }
    val itemImage by lazy { find<ImageButton>(R.id.categoryItemImageButton) }
    val itemNameText by lazy { find<TextView>(R.id.categoryItemName) }

    init {
        clickableView = itemImage
    }

}