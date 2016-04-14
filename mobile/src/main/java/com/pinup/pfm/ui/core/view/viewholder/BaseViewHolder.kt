package com.pinup.pfm.ui.core.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife
import java.text.FieldPosition

/**
 * Base ViewHolder for list's viewholders
 * Registers the item clicks
 */
open class BaseViewHolder : RecyclerView.ViewHolder {

    var clickableView: View? = null

    constructor(itemView: View?) : super(itemView) {
        ButterKnife.bind(this, itemView);
        clickableView = itemView
    }

    /**
     * List item click listener interface
     */
    interface OnItemClickListener {
        /**
         * A list item is clicked on the list
         * @param view The parent view is clicked
         * @param position The clicked item position
         */
        fun listItemClicked(view: View, position: Int);
    }
}