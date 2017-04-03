package com.pinup.pfm.domain.network.dto.user

import com.google.gson.annotations.SerializedName

/**
 * User related DTOs
 */

/**
 * Register user request DTO
 */
class RegisterUserDTO(@SerializedName("email") val email: String,
                      @SerializedName("password") val password: String,
                      @SerializedName("Currency") val currency: String = "HUF") {
    @SerializedName("confirmPassword")
    val confirmPassword: String = password
}