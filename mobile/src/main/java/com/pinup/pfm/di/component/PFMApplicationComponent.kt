package com.pinup.pfm.di.component

import android.accounts.AccountManager
import android.content.Context
import android.content.SharedPreferences
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.patloew.rxlocation.RxLocation
import com.pinup.pfm.di.module.*
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.pinup.pfm.di.qualifiers.Facebook
import com.pinup.pfm.domain.manager.auth.IAuthenticationManager
import com.pinup.pfm.domain.manager.preferences.SharedPreferencesManager
import com.pinup.pfm.domain.manager.transaction.ITransactionManager
import com.pinup.pfm.domain.network.service.AuthService
import com.pinup.pfm.domain.network.service.UserService
import com.pinup.pfm.domain.network.utility.base.BaseNetworkErrorListener
import com.pinup.pfm.domain.provider.IChartDataProvider
import com.pinup.pfm.domain.repository.manager.category.ICategoryRepository
import com.pinup.pfm.domain.repository.manager.transaction.ITransactionRepository
import com.pinup.pfm.domain.repository.manager.user.IUserRepository
import com.pinup.pfm.interactor.auth.IAuthInteractor
import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.interactor.social.facebook.IFacebookInteractor
import com.pinup.pfm.interactor.transaction.ITransactionInteractor
import com.pinup.pfm.interactor.user.IUserInteractor
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
        ProviderModule::class, SocialModule::class, NetworkModule::class))
interface PFMApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun daoSession(): DaoSession
    fun pref(): SharedPreferences
    fun locationProvider(): RxLocation

    fun inject(activityModule: ActivityModule)
    fun inject(listener: BaseNetworkErrorListener)

    //region Repositories
    fun categoryDaoManager(): ICategoryRepository

    fun transactionDaoManager(): ITransactionRepository
    fun userDaoManager(): IUserRepository
    //endregion

    //region Network
    fun authService(): AuthService

    fun userService(): UserService
    //endregion

    //region Interactors
    fun categoryInteractor(): ICategoryInteractor

    fun currencyInteractor(): ICurrencyInteractor
    fun storageInteravtor(): IStorageInteractor
    fun transactionInteravtor(): ITransactionInteractor
    fun facebookInteractor(): IFacebookInteractor
    fun userInteractor(): IUserInteractor
    fun authInteractor(): IAuthInteractor
    //endregion

    //region Managers
    fun transactionManager(): ITransactionManager

    fun accountManager(): AccountManager
    fun sharedPrefManager(): SharedPreferencesManager
    fun authenticationManager(): IAuthenticationManager
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
