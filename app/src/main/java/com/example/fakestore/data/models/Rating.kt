package com.example.fakestore.data.models


import androidx.annotation.Keep

@Keep
data class Rating(
    val count: Int,
    val rate: Double
) {
    fun getRateAsFloat(): Float =
        rate.toFloat()
}