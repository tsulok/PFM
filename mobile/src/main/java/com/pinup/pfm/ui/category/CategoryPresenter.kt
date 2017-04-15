package com.pinup.pfm.ui.category

import com.pinup.pfm.interactor.category.ICategoryInteractor
import com.pinup.pfm.common.ui.core.BasePresenter
import javax.inject.Inject

/**
 * Presenter for Category
 */
class CategoryPresenter
@Inject constructor(val categoryInteractor: ICategoryInteractor) : BasePresenter<CategoryScreen>() {

}