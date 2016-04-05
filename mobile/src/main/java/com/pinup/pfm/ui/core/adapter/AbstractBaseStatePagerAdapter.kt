package com.pinup.pfm.ui.core.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import java.util.*

/**
 * Abstract base adapter for viewPagers
 */
abstract class AbstractBaseStatePagerAdapter<T> : FragmentStatePagerAdapter {

    protected var items: MutableList<T>

    constructor(fm: FragmentManager?) : super(fm) {
        this.items = ArrayList()
    }

    constructor(fm: FragmentManager?, items: MutableList<T>) : super(fm) {
        this.items = items
    }


    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun removeAllItems() {
        this.items.clear()
    }

    fun addItem(item: T) {
        this.items.add(item)
    }

    fun removeItem(item: T) {
        this.items.remove(item)
    }

    fun getStoredItem(position: Int): T? {
        if (position >= 0 && position < items.size) {
            return items[position]
        }

        return null
    }

    override fun getCount(): Int {
        return items.size
    }
}