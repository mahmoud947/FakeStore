package com.example.fakestore.ui.fragment.favorites

import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoriteViewModel>(

    layoutId = R.layout.fragment_favorites,
    viewModelClass = FavoriteViewModel::class.java,
    isSharedViewModel = false
) {

    override fun onInitDataBinding() {

    }

    override fun onInitViewModel() {

    }

}