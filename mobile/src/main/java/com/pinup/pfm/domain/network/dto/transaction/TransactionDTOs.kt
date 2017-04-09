package com.pinup.pfm.domain.network.dto.transaction

import com.google.gson.annotations.SerializedName
import com.pinup.pfm.domain.network.dto.category.CategoryNetworkResponseModel
import java.util.*

/**
 * DTOs for transaction related network calls
 */

class TransactionRequestDTO(
        @SerializedName("localId") val localId: String,
        @SerializedName("id") val serverId: String? = null,
        @SerializedName("date") val date: Date,
        @SerializedName("name") val name: String,
        @SerializedName("latitude") val latitude: Double? = null,
        @SerializedName("longitude") val longitude: Double? = null,
        @SerializedName("imageUrl") val imageUrl: String? = null,
        @SerializedName("amount") val amount: Double,
        @SerializedName("currency") val currency: String,
        @SerializedName("description") val description: String? = null,
        @SerializedName("categoryId") val categoryId: String? = null
)

class TransactionItemDTO(
        @SerializedName("Id") val serverId: String,
        @SerializedName("IsDeleted") val isDeleted: Boolean,
        @SerializedName("Name") val name: String?,
        @SerializedName("Description") val description: String?,
        @SerializedName("Date") val date: Date,
        @SerializedName("PlaceName") val placeName: String?,
        @SerializedName("PlaceLat") val latitude: Double?,
        @SerializedName("PlaceLon") val longitude: Double?,
        @SerializedName("Image") val imageUrl: String?,
        @SerializedName("Amount") val amount: Double,
        @SerializedName("Currency") val currency: String,
        @SerializedName("Category") val category: CategoryNetworkResponseModel?
)

class TransactionUploadRequestDTO(
        @SerializedName("transactions") val items: List<TransactionRequestDTO>
)