package com.pinup.pfm.ui.widget.helper

import android.content.Context
import android.graphics.Typeface
import java.util.*

/**
 * A Helper for fonts - It caches the a read font
 */
class FontCache {

    companion object {
        @JvmStatic val fontCache: Hashtable<String, Typeface> = Hashtable();

        @JvmStatic fun getTypeFace(name: String, context: Context): Typeface? {
            var typeFace = fontCache[name]
            if (typeFace == null) {
                try {
                    typeFace = Typeface.createFromAsset(context.assets, name)
                } catch (e: Exception) {
                    return null
                }
                fontCache.put(name, typeFace);
            }
            return typeFace;
        }
    }
}