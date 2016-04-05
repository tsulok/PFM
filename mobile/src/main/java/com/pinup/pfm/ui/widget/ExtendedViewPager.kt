package com.pinup.pfm.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.pinup.pfm.R

/**
 * A viewpager with extended functionality
 * Supports:
 *  - Disableable paging
 *
 */
class ExtendedViewPager : ViewPager {

    private var isViewPagerEnabled : Boolean = true; // Indicates whether the pager is be able to swiped

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setPagerSwipeEnabled(context, attrs)
    }

    /**
     * Initialize pager from style
     * @param context
     * @param attrs
     */
    private fun setPagerSwipeEnabled(context: Context?, attrs: AttributeSet?) {
        val typedArray: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.ExtendedViewPager)
        isViewPagerEnabled = !(typedArray?.getBoolean(R.styleable.ExtendedViewPager_swipeDisabled, false) ?: false)

        typedArray?.recycle();
    }

    fun setViewPagerEnabled(isEnabled: Boolean) {
        this.isViewPagerEnabled = isEnabled
    }

    fun isViewPagerEnabled(): Boolean {
        return isViewPagerEnabled
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (isViewPagerEnabled) {
            return super.onInterceptTouchEvent(ev);
        }

        return false;
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (isViewPagerEnabled) {
            return super.onTouchEvent(ev);
        }

        return false;
    }
}