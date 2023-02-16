package com.example.fakestore.data.models


import androidx.annotation.Keep

@Keep
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)