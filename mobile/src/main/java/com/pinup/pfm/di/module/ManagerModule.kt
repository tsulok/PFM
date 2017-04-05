package com.pinup.pfm.di.module

import com.pinup.pfm.domain.manager.auth.AuthenticationManager
import com.pinup.pfm.domain.manager.auth.IAuthenticationManager
import com.pinup.pfm.domain.manager.content.ContentManager
import com.pinup.pfm.domain.manager.content.IContentManager
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.domain.manager.transaction.TransactionManager
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
}