package com.pinup.pfm.domain.network.dto.base

import com.google.gson.annotations.SerializedName

/**
 * Model for base network models
 */
class BaseNetworkResponseModel<T> {
    @SerializedName("Success")
    var success: Boolean = false

    @SerializedName("Message")
    var message: String? = null

    @SerializedName("Data")
    var data: T? = null
}