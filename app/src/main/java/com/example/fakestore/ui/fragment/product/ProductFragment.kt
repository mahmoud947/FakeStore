package com.example.fakestore.ui.fragment.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentProductBinding,ProductViewModel>(
    layoutId = R.layout.fragment_product,
    viewModelClass = ProductViewModel::class.java,
    isSharedViewModel = false
) {
    override fun onInitDataBinding() {

    }

    override fun onInitViewModel() {

    }

}