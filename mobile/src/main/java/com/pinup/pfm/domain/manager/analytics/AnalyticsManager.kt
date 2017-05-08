package com.pinup.pfm.domain.manager.analytics

import com.pinup.pfm.domain.analytics.GoogleAnalyticsProvider
import com.pinup.pfm.domain.analytics.base.IAnalyticsProvider
import com.pinup.pfm.domain.analytics.base.IRemoteLoggerException
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Manager for handling analytics
 */
@Singleton
class AnalyticsManager
@Inject constructor(googleAnalyticsProvider: GoogleAnalyticsProvider) : IAnalyticsManager {

    private val providers: MutableList<IAnalyticsProvider>

    init {
        providers = ArrayList()

        // Add here additional providers if needed
        providers.add(googleAnalyticsProvider)
    }

    override fun sendScreenName(screenName: String) {
        for (provider in providers) {
            provider.sendScreenName(screenName)
        }
    }

    override fun sendAction(category: String, action: String, label: String?, vararg args: String) {
        for (provider in providers) {
            provider.sendAction(category, action, label, *args)
        }
    }

    override fun sendAction(category: String, action: String, label: String?, value: Long?) {
        for (provider in providers) {
            provider.sendAction(category, action, label, value)
        }
    }

    override fun <T> sendException(exception: T) where T : Throwable, T : IRemoteLoggerException {
        for (provider in providers) {
            provider.sendException(exception)
        }
    }
}

/**
 * Supported actions on analytics manager
 */
interface IAnalyticsManager : IAnalyticsProvider
