package com.pinup.pfm.domain.network.service

import com.pinup.pfm.domain.network.dto.base.BaseNetworkResponseModel
import com.pinup.pfm.domain.network.dto.transaction.TransactionItemDTO
import com.pinup.pfm.domain.network.dto.transaction.TransactionRequestDTO
import com.pinup.pfm.domain.network.dto.transaction.TransactionUploadRequestDTO
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Transaction related api calls
 */
interface TransactionService {

    companion object Constants {
        const val BASE_PATH = "api/transactions"
    }

    @GET("${Constants.BASE_PATH}/list")
    fun listTransactions(): Observable<BaseNetworkResponseModel<List<TransactionItemDTO>>>

    @POST("${Constants.BASE_PATH}/saveBulk")
    fun uploadTransactions(@Body uploadRequest: TransactionUploadRequestDTO): Observable<List<TransactionItemDTO>>

    @POST("${Constants.BASE_PATH}/save")
    fun uploadTransactions(@Body uploadRequest: TransactionRequestDTO): Observable<BaseNetworkResponseModel<String>>

    @POST("${Constants.BASE_PATH}/delete/{id}")
    fun deleteTransaction(@Path("id") id: String): Observable<Void>
}