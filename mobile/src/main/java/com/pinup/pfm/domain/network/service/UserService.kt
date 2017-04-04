package com.pinup.pfm.domain.network.service

import com.pinup.pfm.domain.network.dto.user.EditUserDTO
import com.pinup.pfm.domain.network.dto.user.RegisterUserDTO
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Service for user related api calls
 */
interface UserService {

    companion object Constants {
        const val BASE_PATH = "api/account"
    }

    @POST("${Constants.BASE_PATH}/register")
    fun register(@Body registerModel: RegisterUserDTO): Observable<Void>

    @POST("${Constants.BASE_PATH}/update")
    fun update(@Body editUserModel: EditUserDTO): Observable<Void>
}