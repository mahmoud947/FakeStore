package com.example.fakestore.data.repository

import android.content.SharedPreferences
import com.example.fakestore.core.data.BaseRepository
import com.example.fakestore.data.models.request.Credentials
import com.example.fakestore.data.models.response.LoginResponse
import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.utils.TOKEN_KEY
import com.example.fakestore.data.remote.utils.USER_ID
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(
    private val api: FakeStoreApi, @Named("Auth") private val prefs: SharedPreferences
) : BaseRepository() {

    suspend fun login(credentials: Credentials) {
        val user: LoginResponse = api.login(credentials)
        saveToken(user.token)
        saveUserID(user.id)
    }

    private fun saveToken(token: String?) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    private fun saveUserID(userId: Int) {
        prefs.edit().putInt(USER_ID, userId).apply()
    }

    fun getUserID(): Int? = prefs.getInt(USER_ID, -1)

    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)
}