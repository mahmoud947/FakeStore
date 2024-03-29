package com.example.fakestore.data.repository

import android.content.SharedPreferences
import com.example.fakestore.core.data.BaseRepository
import com.example.fakestore.data.models.request.Credentials
import com.example.fakestore.data.models.response.LoginResponse
import com.example.fakestore.data.models.response.User
import com.example.fakestore.data.remote.FakeStoreApi
import com.example.fakestore.data.remote.utils.TOKEN_KEY
import com.example.fakestore.data.remote.utils.USER_ID
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Named

class AuthRepository @Inject constructor(
    private val api: FakeStoreApi, @Named("Auth") private val prefs: SharedPreferences,
    private val auth:FirebaseAuth
) : BaseRepository() {

    suspend fun login(credentials: Credentials) {
        val user: LoginResponse = api.login(credentials)
        saveToken(user.token)
        saveUserID(user.id)
    }

     fun signWithGoogle(email:String,password:String): Task<AuthResult> {
      return  auth.createUserWithEmailAndPassword(email,password)
    }

    suspend fun getUserInfo(): User {
        return api.getUserInfo(getUserID())
    }

    private fun saveToken(token: String?) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    private fun saveUserID(userId: Int) {
        prefs.edit().putInt(USER_ID, userId).apply()
    }

    private fun getUserID(): Int = prefs.getInt(USER_ID, -1)

    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)
}