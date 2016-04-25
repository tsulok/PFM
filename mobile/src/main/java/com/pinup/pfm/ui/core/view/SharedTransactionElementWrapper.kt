package com.pinup.pfm.ui.core.view

import android.view.View

/**
 * A shared transaction element wrapper, in order to resolve varargs problems
 */
data class SharedTransactionElementWrapper(val view: View, val identifier: String) {
}