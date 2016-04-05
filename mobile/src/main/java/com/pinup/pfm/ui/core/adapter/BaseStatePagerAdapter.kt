package com.pinup.pfm.ui.core.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.pinup.pfm.ui.core.view.IFragmentFactory

/**
 * Base state pager adapter for viewPagers
 */
open class BaseStatePagerAdapter<T: IFragmentFactory>: AbstractBaseStatePagerAdapter<T> {

    constructor(fm: FragmentManager?) : super(fm)

    constructor(fm: FragmentManager?, items: MutableList<T>) : super(fm, items)

    override fun getItem(position: Int): Fragment? {
        val storedItem = getStoredItem(position)
        return storedItem?.getFragment()
    }
}