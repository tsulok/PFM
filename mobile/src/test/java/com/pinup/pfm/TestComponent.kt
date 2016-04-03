package com.pinup.pfm

import com.pinup.pfm.interactor.InteractorModule
import com.pinup.pfm.test.dao.category.CategoryTestCreate
import com.pinup.pfm.test.dao.category.CategoryTestRead
import dagger.Component
import javax.inject.Singleton

/**
 * Component for supported DI components in test environment
 */
@Singleton
@Component(modules = arrayOf(InteractorModule::class, TestModule::class) )
interface TestComponent : PFMApplicationComponent {

    fun inject(categoryTestCreate: CategoryTestCreate)
    fun inject(categoryTestRead: CategoryTestRead)
}