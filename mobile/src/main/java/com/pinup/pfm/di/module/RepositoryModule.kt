package com.pinup.pfm.di.module

import com.pinup.pfm.domain.repository.manager.category.CategoryDaoManager
import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import dagger.Binds
import dagger.Module

/**
 * DI modules for Repositories
 */
@Module
abstract class RepositoryModule {

    @Binds abstract fun categoryDaoManager(manager: CategoryDaoManager): ICategoryRepository
}