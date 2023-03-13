package com.example.fakestore.ui.fragment.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {
    private val _authState = MutableLiveData<DataState<Boolean>>()
    val authState: LiveData<DataState<Boolean>> get() = _authState


    fun signUp(email: String, password: String) {
        authRepository.signWithGoogle(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _authState.value = DataState.Success(true)
                it.exception?.printStackTrace()
            } else {
                _authState.value = DataState.Error(it.exception)
                it.exception?.printStackTrace()
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

}