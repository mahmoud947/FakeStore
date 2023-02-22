package com.example.fakestore.ui.fragment.favorites

import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.FragmentFavoritesBinding
import com.example.fakestore.ui.fragment.favorites.adapters.FavoritesProductAdapter
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
        setHasOptionsMenu(true)
        favoriteLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        favoriteProductAdapter =
            FavoritesProductAdapter(interaction = object : FavoritesProductAdapter.Interaction {
                override fun onItemSelected(position: Int, item: Product) {
                    findNavController().navigate(
                        FavoritesFragmentDirections.toDetailScreen(
                            item.id,
                            item.title
                        )
                    )
                }

            })

        binding.rvFavoritesProducts.apply {
            layoutManager = favoriteLayoutManager
            adapter = favoriteProductAdapter
        }
    }

    override fun onInitViewModel() {
        viewModel.favoriteProducts.observe(viewLifecycleOwner) {
            favoriteProductAdapter.isLoading = false
            favoriteProductAdapter.submitList(it)
        }
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

}