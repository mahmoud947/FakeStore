package com.example.fakestore.data.repository

import android.content.SharedPreferences
import com.example.fakestore.core.data.BaseRepository
import com.example.fakestore.data.models.request.Credentials
import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.utils.TOKEN_KEY
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(
    private val api: FakeStoreApi, @Named("Auth") private val prefs: SharedPreferences
) : BaseRepository() {

    suspend fun login(credentials: Credentials) = api.login(credentials)

    fun saveToken(token: String?) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)
}