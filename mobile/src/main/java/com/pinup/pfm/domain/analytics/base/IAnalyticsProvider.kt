package com.pinup.pfm.domain.analytics.base

/**
 * Interface for common analytics provider
 */

interface IAnalyticsProvider {

    /**
     * Send screen name with the configured provider

     * @param screenName The name of the screen
     */
    fun sendScreenName(screenName: String)

    /**
     * Send an action for a category with the configured provider

     * @param category The name of the category
     * *
     * @param action   The name of the action
     * *
     * @param label    An optional label
     * *
     * @param args     Possible arguments
     */
    fun sendAction(category: String, action: String, label: String?, vararg args: String)

    /**
     * Send an action for a category with the configured provider

     * @param category The name of the category
     * *
     * @param action   The name of the action
     * *
     * @param label    An optional label
     * *
     * @param value    An optional value to this label
     */
    fun sendAction(category: String, action: String, label: String?, value: Long?)

    /**
     * Send an exception with the configured provider

     * @param exception The exception to be logged
     */
    fun <T> sendException(exception: T) where T : Throwable, T : IRemoteLoggerException
}

/**
 * Interface for custom exception logger
 */
interface IRemoteLoggerException {
    /**
     * Returns the message of the exception

     * @return The message of the exception
     */
    val message: String

    /**
     * Returns a flag whether the exception was fatal or not

     * @return The exception is fatal or not
     */
    val isFatal: Boolean
}