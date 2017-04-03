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
        @SerializedName("password") val password: String,
        @SerializedName("grant_type") val loginType: String = "password")

/**
 * Request for email requests
 */
class LoginFacebookAuthNetworkModel(
        @SerializedName("fbToken") val token: String,
        @SerializedName("grant_type") val loginType: String = "facebook")

/**
 * Response for login
 */
class LoginResponseModel(@SerializedName("userName") var userName: String? = null,
                         @SerializedName("access_token") var accessToken: String? = null)

/**
 * Forgot user password request DTO
 */
class ForgotUserPasswordDTO(@SerializedName("email") val email: String)

