package com.pinup.pfm.domain.network.dto.transaction

import com.google.gson.annotations.SerializedName
import com.pinup.pfm.domain.network.dto.category.CategoryNetworkResponseModel
import java.util.*

/**
 * DTOs for transaction related network calls
 */

class TransactionRequestDTO(
        @SerializedName("localId") val localId: String,
        @SerializedName("uuid") val serverId: String,
        @SerializedName("date") val date: Date,
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longitude") val longitude: Double,
        @SerializedName("imageUrl") val imageUrl: String,
        @SerializedName("amount") val amount: Double,
        @SerializedName("currency") val currency: String,
        @SerializedName("descriptionText") val description: String,
        @SerializedName("categoryId") val categoryId: String
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
        @SerializedName("transactions") val items: List<TransactionItemDTO>
)