package com.example.fakestore.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converter {

    @TypeConverter
    fun listOfStringToJson(strings: List<String>): String {
        return Gson().toJson(strings)
    }

    @TypeConverter
    fun listOfStringFromJson(json: String): List<String> {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, listType)
    }
}