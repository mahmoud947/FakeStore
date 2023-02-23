package com.example.fakestore.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseViewModel
import com.example.fakestore.data.models.response.User
import com.example.fakestore.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<DataState<User>>()
    val user: LiveData<DataState<User>> get() = _user

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        handleData(filterCriteria = {
            authRepository.getUserInfo()
        }, data = _user)
    }
}