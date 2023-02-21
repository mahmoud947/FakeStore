package com.example.fakestore.data.models.response


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DecimalFormat
import kotlin.math.round
import kotlin.math.roundToInt

@Keep
@Entity
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
) {
    fun ratingAsFloat(): Float = this.rating.toFloat()
    fun getPriceAfterDiscount(): String {
        val priceAsDouble = this.price - (discountPercentage / 100).toFloat()
        val df = DecimalFormat("#.##")
        return df.format(priceAsDouble)
    }

}