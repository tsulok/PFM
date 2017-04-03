package com.pinup.pfm.domain.network.service

import com.pinup.pfm.domain.network.dto.auth.LoginMailAuthNetworkModel
import com.pinup.pfm.domain.network.dto.auth.LoginResponseModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Service for authentication
 */
interface AuthService {

    @POST("/token")
    fun login(@Body request: LoginMailAuthNetworkModel): Observable<LoginResponseModel>
}