package com.example.fakestore.data.remote

import com.example.fakestore.BuildConfig
import com.example.fakestore.data.models.response.RandomPhotoResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("photos/random")
    suspend fun getRandomPhoto(
        @Query("client_id")
        clientId: String = BuildConfig.CLIENT_ID,
        @Query("count")
        count: Int = 1,
        @Query("query")
        query: String = "fashion models"
    ):List<RandomPhotoResponseItem>
}