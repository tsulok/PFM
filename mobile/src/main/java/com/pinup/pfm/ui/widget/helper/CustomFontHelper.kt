package com.pinup.pfm.ui.widget.helper

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.pinup.pfm.R

/**
 * A Helper class which helps to set custom fonts to a textview
 */
class CustomFontHelper {

    companion object {

        /**
         * Sets a font on a textview based on the custom com.my.package:font attribute
         * If the custom font attribute isn't found in the attributes nothing happens
         * @param textView
         * @param context
         * @param attrs
         */
        @JvmStatic fun setCustomFont(textView: TextView, context: Context?, attrs: AttributeSet?) {
            val attr = context?.obtainStyledAttributes(attrs, R.styleable.CustomFont);
            val font = attr?.getString(R.styleable.CustomFont_fontType);
            setCustomFont(textView, font, context);

            attr?.recycle();
        }

        /**
         * Sets a font on a textview
         * @param textView
         * @param font
         * @param context
         */
        @JvmStatic fun setCustomFont(textView: TextView, font: String?, context: Context?) {
            if(font == null || context == null) {
                return;
            }

            val typeFace = FontCache.getTypeFace(font, context);
            if (typeFace != null) {
                textView.typeface = typeFace;
            }
        }
    }
}