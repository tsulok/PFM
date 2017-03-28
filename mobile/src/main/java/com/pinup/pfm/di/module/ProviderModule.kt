package com.pinup.pfm.di.module

import com.pinup.pfm.domain.provider.ChartDataProvider
import com.pinup.pfm.domain.provider.IChartDataProvider
import dagger.Binds
import dagger.Module

/**
 * Module for providers
 */
@Module
abstract class ProviderModule {

    @Binds abstract fun chartDataProvider(provider: ChartDataProvider): IChartDataProvider
}