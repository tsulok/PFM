package com.pinup.pfm.di.qualifiers

import javax.inject.Qualifier

/**
 * Separate qualifiers
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Network

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ChildFragmentManager

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SupportFragmentManager