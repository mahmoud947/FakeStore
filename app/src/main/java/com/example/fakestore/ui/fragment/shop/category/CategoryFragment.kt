package com.example.fakestore.ui.fragment.shop.category

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.FragmentCategoryBinding
import com.example.fakestore.ui.fragment.shop.ShopViewModel
import com.example.fakestore.ui.fragment.shop.category.adapters.CategoryAdapter
import com.example.fakestore.utils.recyclerview.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment(
    private val category: String = ""
) : BaseFragment<FragmentCategoryBinding, ShopViewModel>(
    layoutId = R.layout.fragment_category,
    viewModelClass = ShopViewModel::class.java,
    isSharedViewModel = false
) {
    private lateinit var productAdapter: CategoryAdapter
    private lateinit var productLayoutManger: WrapContentLinearLayoutManager


    override fun onInitDataBinding() {
        productAdapter = CategoryAdapter(interaction = object : CategoryAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Product) {
                val bundle = bundleOf("productId" to item.id, "productTitle" to item.title)
                findNavController().navigate(
                    R.id.toDetailScreen,
                    bundle
                )
            }

            override fun onAddToFavoriteClicked(item: Product) {
                viewModel.addProductTOFavorite(item)
            }

        })
        productLayoutManger =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCategory.apply {
            layoutManager = productLayoutManger
            adapter = productAdapter
            setHasFixedSize(true)
        }
        viewModel.getProducts(category)
    }

    override fun onInitViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { productsDataState ->
            productAdapter.isLoading = productsDataState.isLoading()
            productsDataState.getData()?.let {
                productAdapter.submitList(it)
            }
        }
    }


    companion object {
        var category = "category"

        @JvmStatic
        fun newInstance(category: String) =
            CategoryFragment(category).apply {
                arguments = Bundle().apply {
                    putString(category, category)
                }
            }
    }

}