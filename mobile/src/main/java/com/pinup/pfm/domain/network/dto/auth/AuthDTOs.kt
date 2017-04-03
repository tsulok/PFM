package com.pinup.pfm.domain.network.dto.auth

import com.google.gson.annotations.SerializedName

/**
 * Authentication DTOs
 */

/**
 * Request for email requests
 */
class LoginMailAuthNetworkModel(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String) {

    @SerializedName("grant_type")
    val loginType: String = "password"
}

/**
 * Response for login
 */
class LoginResponseModel {

    @SerializedName("userName")
    var userName: String? = null

    @SerializedName("access_token")
    var accessToken: String? = null
}