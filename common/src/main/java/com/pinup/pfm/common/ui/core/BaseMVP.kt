package com.pinup.pfm.common.ui.core

/**
 * Base classes for MVP
 */

interface IBasePresenter {
    fun bind(screen: BaseScreen)
    fun unbind()
}

/**
 * Base screen interface
 */
interface BaseScreen {
}

interface EmptyScreen: BaseScreen {

}

/**
 * Abstract presenter class
 */
open class BasePresenter<T: BaseScreen>: IBasePresenter {

    protected var screen: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun bind(screen: BaseScreen) {
        this.screen = screen as T?
    }

    override fun unbind() {
        this.screen = null
    }
}