package com.pinup.pfm.ui.core.view

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

interface IBasePresenter {
    fun bind(screen: BaseScreen)
    fun unbind()
}

interface EmptyScreen: BaseScreen {

}