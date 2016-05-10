package com.pinup.pfm.ui.core.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pinup.pfm.ui.core.view.viewholder.BaseViewHolder
import java.util.*

/**
 * BaseAdapter for handling data in lists
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder> {

    companion object {
        @JvmStatic val INDEX_NOT_FOUND = -1
    }

    protected val context: Context
    var items: MutableList<T>
    var onItemClickListener: BaseViewHolder.OnItemClickListener? = null

    /**
     * Default constructor
     */
    constructor(context: Context) : this(context, ArrayList<T>())

    /**
     * Constructor with pre initialized list
     * @param context
     * @param items The new items
     */
    constructor(context: Context, items: List<T>) : super() {
        this.context = context
        this.items = ArrayList<T>(items)
    }

    override fun getItemCount() = items.size

    /**
     * Get the position of the given item
     * @param item The item
     * @return The position of the item if is in the collection, -1 otherwise
     */
    fun getItemPosition(item: T): Int {
        if (items.contains(item)) {
            return items.indexOf(item)
        } else {
            return INDEX_NOT_FOUND
        }
    }

    /**
     * Set a new set of items
     * @param items The new items
     */
    fun clearAndSetItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Add new item to the end of the list
     * @param item The new item
     */
    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    /**
     * Add new item to the list to the specified position
     * @param position The position to insert
     * @param item The new item
     */
    fun addItem(position: Int, item: T) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    /**
     * Add a list to the end of the existing list
     * @param items The new list of items
     */
    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Remove the given item from the list
     * @param item The removable item
     */
    fun removeItem(item: T) {
        if (items.contains(item)) {
            val position = items.indexOf(item)
            items.remove(item)
            notifyItemRemoved(position)
        }
    }

    /**
     * Remove item via it's position in the list
     * @param position The item's position
     */
    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Remove all items from the list
     */
    fun removeAllItems() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    /**
     * Notify item is changed in the list
     * @param item The changed item
     */
    fun notifyItemChanged(item: T) {
        if (items.contains(item)) {
            notifyItemChanged(items.indexOf(item))
        }
    }

    /**
     * Prepares the selected view for handling the tap if it was set before
     * @param vh The actual viewholder
     */
    protected fun prepareItemOnClick(vh: BaseViewHolder) {
        val view = vh.clickableView
        if (view != null) {
            view.setOnClickListener {
                onItemClickListener?.listItemClicked(view, vh.adapterPosition)
            }
            view.tag = vh
        }
    }
}