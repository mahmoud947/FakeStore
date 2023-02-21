package com.example.fakestore.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.request.Credentials
import com.example.fakestore.data.repository.AuthRepository
import com.example.fakestore.utils.loginErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _navigateToHome = MutableLiveData<DataState<Boolean>>()
    val navigateToHome: LiveData<DataState<Boolean>> get() = _navigateToHome
    fun login(username: String, password: String) {
        _navigateToHome.value = DataState.Loading
        viewModelScope.launch(Dispatchers.IO + loginErrorHandler(_navigateToHome)) {
            val response =
                authRepository.login(Credentials(username = username, password = password))
            saveToken(response.token)
            if (getToken() != null) {
                _navigateToHome.postValue(DataState.Success(true))
            }
        }
    }


    fun isUserNameValid(userName: String): String? {
        return if (userName.isBlank()) {
            "User name is Required"
        } else {
            null
        }
    }

    fun isPasswordValid(password: String): String? {
        return if (password.isBlank()) {
            "Password name is Required"
        } else {
            null
        }
    }

    private fun saveToken(token: String) = authRepository.saveToken(token)
    private fun getToken(): String? = authRepository.getToken()
}