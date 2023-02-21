package com.example.fakestore.ui.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _navigateToMain = MutableLiveData<AuthState>()
    val navigateToMain: LiveData<AuthState> get() = _navigateToMain

    init {
        navigate()
    }

    private fun navigate() {
        viewModelScope.launch {
            delay(1000)
            _navigateToMain.value = AuthState.AUTHENTICATED
        }
    }

}

enum class AuthState {
    AUTHENTICATED,
    UNAUTHENTICATED,
}