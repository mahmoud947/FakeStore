package com.example.fakestore.ui.fragment.favorites

import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentFavoritesBinding
import com.example.fakestore.ui.fragment.favorites.adapters.FavoritesProductAdapter
import com.example.fakestore.utils.recyclerview.AutoFitGridLayoutManager
import com.example.fakestore.utils.recyclerview.WrapContentGridLayoutManager
import com.example.fakestore.utils.recyclerview.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoriteViewModel>(
    layoutId = R.layout.fragment_favorites,
    viewModelClass = FavoriteViewModel::class.java,
    isSharedViewModel = false
) {
    lateinit var favoriteLayoutManager: WrapContentLinearLayoutManager
    lateinit var favoriteProductAdapter: FavoritesProductAdapter
    override fun onInitDataBinding() {
        favoriteLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        favoriteProductAdapter = FavoritesProductAdapter()

        binding.rvFavoritesProducts.apply {
            layoutManager = favoriteLayoutManager
            adapter = favoriteProductAdapter
        }
    }

    override fun onInitViewModel() {
        viewModel.favoriteProducts.observe(this) {
            favoriteProductAdapter.isLoading = false
            favoriteProductAdapter.submitList(it)
        }
    }

}