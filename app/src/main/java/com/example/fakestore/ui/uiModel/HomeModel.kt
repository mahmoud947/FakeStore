package com.example.fakestore.ui.uiModel

import com.example.fakestore.data.models.response.Product

data class HomeModel(
    val products:List<Product>,
    val url:String
)
