package com.pinup.pfm.ui.history.adapter.viewholder

import android.view.View
import android.widget.TextView
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import com.pinup.pfm.ui.core.view.viewholder.find

/**
 * Viewholder for history item
 */
class HistoryViewHolder(itemView: View?) : BaseViewHolder(itemView) {

    val nameTextView by lazy { find<TextView>(R.id.name) }
    val timeTextView by lazy { find<TextView>(R.id.time) }
    val amountTextView by lazy { find<TextView>(R.id.amount) }
    val currencyTextView by lazy { find<TextView>(R.id.currency) }
}