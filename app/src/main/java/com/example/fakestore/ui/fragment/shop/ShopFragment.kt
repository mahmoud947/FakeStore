package com.example.fakestore.ui.fragment.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentShopBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShopBinding, ShopViewModel>(
    layoutId = R.layout.fragment_shop,
    viewModelClass = ShopViewModel::class.java,
    isSharedViewModel = false

) {

    override fun onInitDataBinding() {

    }

    override fun onInitViewModel() {

    }

}