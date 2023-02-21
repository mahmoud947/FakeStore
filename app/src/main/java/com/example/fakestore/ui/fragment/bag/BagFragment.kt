package com.example.fakestore.ui.fragment.bag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentBagBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : BaseFragment<FragmentBagBinding,BagViewModel>(
    layoutId = R.layout.fragment_bag,
    viewModelClass = BagViewModel::class.java,
    isSharedViewModel = false
) {

    override fun onInitDataBinding() {

    }

    override fun onInitViewModel() {

    }

}