package com.pinup.pfm.di.component

import android.content.Context
import android.content.SharedPreferences
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.patloew.rxlocation.RxLocation
import com.pinup.pfm.di.module.*
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.di.qualifiers.Facebook
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.domain.provider.IChartDataProvider
import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import com.pinup.pfm.domain.repository.manager.transaction.ITransactionRepository
import com.pinup.pfm.domain.repository.manager.user.IUserRepository
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.social.facebook.IFacebookInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.interactor.utils.ICurrencyInteractor
import com.pinup.pfm.interactor.utils.IStorageInteractor
import com.pinup.pfm.model.database.DaoSession
import dagger.Component
import javax.inject.Singleton

/**
 * Component for supported DI components
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, InteractorModule::class, DaoModule::class,
        PresenterModule::class, UtilityModule::class, RepositoryModule::class, ManagerModule::class,
        ProviderModule::class, SocialModule::class))
interface PFMApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun daoSession(): DaoSession
    fun pref(): SharedPreferences
    fun locationProvider(): RxLocation

    fun inject(activityModule: ActivityModule)

    //region Repositories
    fun categoryDaoManager(): ICategoryRepository

    fun transactionDaoManager(): ITransactionRepository
    fun userDaoManager(): IUserRepository
    //endregion

    //region Interactors
    fun categoryInteractor(): ICategoryInteractor

    fun currencyInteractor(): ICurrencyInteractor
    fun storageInteravtor(): IStorageInteractor
    fun transactionInteravtor(): ITransactionInteractor
    fun facebookInteractor(): IFacebookInteractor
    //endregion

    //region Managers
    fun transactionManager(): ITransactionManager
    //endregion

    //region Providers
    fun chartDataProvider(): IChartDataProvider
    //endregion

    //region Social
    @Facebook
    fun callbackManager(): CallbackManager

    @Facebook
    fun loginManager(): LoginManager
    //endregion
}
