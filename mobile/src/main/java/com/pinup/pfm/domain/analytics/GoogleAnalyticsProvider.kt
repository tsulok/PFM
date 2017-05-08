package com.pinup.pfm.domain.analytics

import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.pinup.pfm.domain.analytics.base.IAnalyticsProvider
import com.pinup.pfm.domain.analytics.base.IRemoteLoggerException
import javax.inject.Inject


class GoogleAnalyticsProvider
@Inject constructor(private val tracker: Tracker) : IAnalyticsProvider {

    override fun sendScreenName(screenName: String) {
        tracker.setScreenName(screenName)
        tracker.send(HitBuilders.ScreenViewBuilder().build())
    }

    override fun sendAction(category: String, action: String, label: String?, vararg args: String) {
        val eventBuilder = HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)

        if (label != null) {
            eventBuilder.setLabel(label)
        }
        tracker.send(eventBuilder.build())
    }

    override fun sendAction(category: String, action: String, label: String?, value: Long?) {
        val eventBuilder = HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)

        if (label != null) {
            eventBuilder.setLabel(label)
        }

        if (value != null) {
            eventBuilder.setValue(value)
        }

        tracker.send(eventBuilder.build())
    }

    override fun <T> sendException(exception: T) where T : Throwable, T : IRemoteLoggerException {
        tracker.send(HitBuilders.ExceptionBuilder()
                .setDescription(exception.message)
                .setFatal(exception.isFatal)
                .build())
    }

}