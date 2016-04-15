package com.pinup.pfm.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import com.pinup.pfm.ui.widget.helper.CustomFontHelper

/**
 * An extended button which has the additional functions
 *  - Set Custom Font
 */
class ExtendedButton : Button {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        CustomFontHelper.setCustomFont(this, context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        CustomFontHelper.setCustomFont(this, context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        CustomFontHelper.setCustomFont(this, context, attrs)
    }
}