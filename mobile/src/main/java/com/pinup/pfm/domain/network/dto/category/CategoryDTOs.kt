package com.pinup.pfm.domain.network.dto.category

import com.google.gson.annotations.SerializedName

/**
 * Category related network DTOs
 */
class CategoryNetworkResponseModel(
        @SerializedName("ParentCategoryId") var parentId: String? = null,
        @SerializedName("Children") var children: MutableList<CategoryNetworkResponseModel> = ArrayList(),
        @SerializedName("Name") var name: String = "",
        @SerializedName("IsDeleted") var isDeleted: Boolean = false,
        @SerializedName("IconUrl") var imageUrl: String = "")