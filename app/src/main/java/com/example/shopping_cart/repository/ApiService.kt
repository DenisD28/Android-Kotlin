package com.example.shopping_cart.repository

import com.example.shopping_cart.Model.ProductItemResponse
import com.example.shopping_cart.Model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products/{name}")
    suspend fun getProducts(@Path("name") category: String): Response<ProductResponse>

    @GET("products/category/{category}")
    suspend fun getCategoryProducts(@Path("category") category: String): Response<ProductResponse>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") category: String): Response<ProductItemResponse>
}