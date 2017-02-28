package com.pinup.pfm.di.module

import com.pinup.pfm.domain.repository.manager.category.CategoryDaoManager
import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import com.pinup.pfm.domain.repository.manager.transaction.ITransactionRepository
import com.pinup.pfm.domain.repository.manager.transaction.TransactionDaoManager
import com.pinup.pfm.domain.repository.manager.user.IUserRepository
import com.pinup.pfm.domain.repository.manager.user.UserDaoManager
import dagger.Binds
import dagger.Module

/**
 * DI modules for Repositories
 */
@Module
abstract class RepositoryModule {

    @Binds abstract fun categoryDaoManager(manager: CategoryDaoManager): ICategoryRepository
    @Binds abstract fun transactionDaoManager(manager: TransactionDaoManager): ITransactionRepository
    @Binds abstract fun userDaoManager(manager: UserDaoManager): IUserRepository
}