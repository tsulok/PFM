package com.pinup.pfm.di.module

import com.pinup.pfm.domain.manager.analytics.AnalyticsManager
import com.pinup.pfm.domain.manager.analytics.IAnalyticsManager
import com.pinup.pfm.domain.manager.auth.AuthenticationManager
import com.pinup.pfm.domain.manager.auth.IAuthenticationManager
import com.pinup.pfm.domain.manager.content.ContentManager
import com.pinup.pfm.domain.manager.content.IContentManager
import com.pinup.pfm.domain.manager.sync.ISyncManager
import com.pinup.pfm.domain.manager.sync.SyncManager
import com.pinup.pfm.domain.manager.sync.SyncManager_Factory
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.domain.manager.transaction.TransactionManager
import com.pinup.pfm.domain.repository.manager.IRepositoryManager
import com.pinup.pfm.domain.repository.manager.RepositoryManager
import dagger.Binds
import dagger.Module

/**
 * DI modules for Repositories
 */
@Module
abstract class ManagerModule {

    @Binds abstract fun transactionManager(manager: TransactionManager): ITransactionManager
    @Binds abstract fun authManager(manager: AuthenticationManager): IAuthenticationManager
    @Binds abstract fun contentManager(manager: ContentManager): IContentManager
    @Binds abstract fun repositoryManager(manager: RepositoryManager): IRepositoryManager
    @Binds abstract fun syncManager(manager: SyncManager): ISyncManager
    @Binds abstract fun analyticManager(manager: AnalyticsManager): IAnalyticsManager
}