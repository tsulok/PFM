package com.pinup.pfm.domain.network.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO for network errors
 */
class NetworkError {
    @SerializedName("type")
    var type: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("message_translated")
    var translatedMessage: String? = null
}