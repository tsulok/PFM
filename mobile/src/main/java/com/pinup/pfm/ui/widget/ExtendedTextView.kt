package com.pinup.pfm.ui.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import com.pinup.pfm.R
import com.pinup.pfm.ui.widget.helper.CustomFontHelper

/**
 * An extended textView which has the additional functions
 * - Set Custom Font
 * - Set Text underlined
 */
class ExtendedTextView : TextView {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    /**
     * Initialize the textview with the basic settings
     * If a single font is provided, set it
     * If multiple font is provided, save it for later use
     *
     * @param context
     * @param attrs
     */
    private fun init(context: Context?, attrs: AttributeSet?) {
        CustomFontHelper.setCustomFont(this, context, attrs);
        this.setTextUnderline(context, attrs);
    }

    override fun isInEditMode(): Boolean {
        return true
    }

    /**
     * Set text underline if it set in xml
     *
     * @param context
     * @param attrs
     */
    private fun setTextUnderline(context: Context?, attrs: AttributeSet?) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.ExtendedTextView);
        val isTextUnderlined = typedArray?.getBoolean(R.styleable.ExtendedTextView_underlined, false) ?: false

        if (isTextUnderlined) {
            drawTextUnderLine();
        }

        typedArray?.recycle();
    }

    fun drawTextUnderLine() {
        this.paintFlags = this.paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
    }

    fun removeTextUnderLine() {
        this.paintFlags = this.paintFlags.and(Paint.UNDERLINE_TEXT_FLAG.inv())
    }
}