package com.example.fakestore.data.models.response


import androidx.annotation.Keep

@Keep
data class RandomPhotoResponseItem(
    val alt_description: String,
    val id: String,
    val promoted_at: Any,
    val sponsorship: Any,
    val updated_at: String,
    val urls: Urls,
)