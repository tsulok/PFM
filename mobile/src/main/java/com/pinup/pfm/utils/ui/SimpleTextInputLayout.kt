package com.pinup.pfm.utils.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet

/**
 * Simple textInputlayout where setting the error won't update the edittext's color
 */
class SimpleTextInputLayout : TextInputLayout {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setError(error: CharSequence?) {
        super.setError(error)
        clearBackgroundChanges()
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        clearBackgroundChanges()
    }

    private fun clearBackgroundChanges() {
        editText?.background?.let(Drawable::clearColorFilter)
    }
}