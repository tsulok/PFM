package com.pinup.pfm.ui.history.adapter.viewholder

import android.view.View
import android.widget.TextView
import butterknife.Bind
import com.pinup.pfm.R
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder

/**
 * Viewholder for history item
 */
class HistoryViewHolder : BaseViewHolder {

    @Bind(R.id.name) lateinit var nameTextView: TextView
    @Bind(R.id.time) lateinit var timeTextView: TextView
    @Bind(R.id.amount) lateinit var amountTextView: TextView
    @Bind(R.id.currency) lateinit var currencyTextView: TextView

    constructor(itemView: View?) : super(itemView) {

    }
}