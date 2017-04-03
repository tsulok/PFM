package com.pinup.pfm.domain.network.service

import com.pinup.pfm.domain.network.dto.auth.LoginResponseModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Service for authentication
 */
interface AuthService {

    @POST("token")
    @FormUrlEncoded
    fun login(@Field("username") userName: String,
              @Field("password") password: String,
              @Field("grant_type") grantType: String = "password"): Observable<LoginResponseModel>

    @POST("token")
    @FormUrlEncoded
    fun loginSocial(@Field("fbToken") token: String,
                    @Field("grant_type") grantType: String = "facebook"): Observable<LoginResponseModel>
}