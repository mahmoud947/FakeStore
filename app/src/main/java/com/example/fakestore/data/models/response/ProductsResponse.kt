package com.example.fakestore.data.models.response


import androidx.annotation.Keep

@Keep
data class ProductsResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)