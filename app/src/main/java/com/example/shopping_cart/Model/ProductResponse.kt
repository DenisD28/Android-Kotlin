package com.example.shopping_cart.Model

import com.google.gson.annotations.SerializedName


data class ProductResponse(
    @SerializedName("products") val products: List<ProductItemResponse>
)

data class ProductItemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Int,
    @SerializedName("category") val category: String,
    @SerializedName("thumbnail") val thumbnail: String
)
