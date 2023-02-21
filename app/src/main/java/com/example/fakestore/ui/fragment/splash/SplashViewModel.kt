package com.example.fakestore.ui.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _navigateToMain = MutableLiveData<AuthState>()
    val navigateToMain: LiveData<AuthState> get() = _navigateToMain

    init {
        navigate()
    }

    private fun navigate() {
        viewModelScope.launch {
            delay(1000)
            if (authRepository.getToken()!=null){
                _navigateToMain.value = AuthState.AUTHENTICATED
            }else{
                _navigateToMain.value = AuthState.UNAUTHENTICATED
            }

        }
    }

}

enum class AuthState {
    AUTHENTICATED,
    UNAUTHENTICATED,
}