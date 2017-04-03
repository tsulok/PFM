package com.pinup.pfm.domain.network.service

import com.pinup.pfm.domain.network.dto.base.BaseNetworkResponseModel
import com.pinup.pfm.domain.network.dto.category.CategoryNetworkResponseModel
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * CategoryService
 */
interface CategoryService {
    companion object Constants {
        const val BASE_PATH = "api/categories"
    }

    @GET("${Constants.BASE_PATH}/list")
    fun listCategories(): Observable<BaseNetworkResponseModel<MutableList<CategoryNetworkResponseModel>>>
}