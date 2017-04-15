package com.pinup.pfm.domain.network.utility.base

import android.content.Context
import com.pinup.pfm.PFMApplication
import com.pinup.pfm.R
import com.pinup.pfm.domain.network.utility.INetworkErrorListener
import com.pinup.pfm.domain.network.dto.NetworkError
import com.pinup.pfm.extensions.string


/**
 * Base implementation for network errors
 */
abstract class BaseNetworkErrorListener(private val baseErrorNetworkView: IBaseErrorNetworkView? = null)
    : INetworkErrorListener {

    lateinit var context: Context

    init {
        PFMApplication.applicationComponent.inject(this)
    }

    override fun onNetworkErrorOccurred() {
        if (baseErrorNetworkView != null) {
            baseErrorNetworkView.onNetworkError()
        } else {
            onHttpErrorOccurred()
        }
    }

    override fun onHttpErrorOccurred(networkError: NetworkError?) {
        this.onHttpErrorOccurred()
    }

    override fun onHttpErrorOccurred() {
        this.onHttpErrorOccurred(context.string(R.string.error_general_network_unknown))
    }

    override fun onErrorNotSupported() {
        this.onHttpErrorOccurred(context.string(R.string.error_general_network_unknown))
    }
}

interface IBaseErrorNetworkView{
    /**
     * Called on network errors
     */
    fun onNetworkError()
}
