package com.example.fakestore.data.models.request
import androidx.annotation.Keep

@Keep
data class Credentials(
    val password: String,
    val username: String
)