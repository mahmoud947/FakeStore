package com.example.fakestore.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding,ProfileViewModel>(
    layoutId = R.layout.fragment_profile,
    viewModelClass = ProfileViewModel::class.java,
    isSharedViewModel = false
) {
    override fun onInitDataBinding() {

    }

    override fun onInitViewModel() {

    }
}