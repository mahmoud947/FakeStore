package com.example.fakestore.ui.fragment.category

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentCategoryBinding
import com.example.fakestore.ui.fragment.home.adapters.ProductAdapter
import com.example.fakestore.utils.recyclerview.AutoFitGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment(
    private val category: String
) : BaseFragment<FragmentCategoryBinding, CategoryViewModel>(
    layoutId = R.layout.fragment_category,
    viewModelClass = CategoryViewModel::class.java,
    isSharedViewModel = true
) {
    lateinit var productAdapter: ProductAdapter
    lateinit var productLayoutManger:AutoFitGridLayoutManager


    override fun onInitDataBinding() {
        productAdapter = ProductAdapter()
        productLayoutManger = AutoFitGridLayoutManager(requireContext(),500)
        binding.rvCategory.apply {
            layoutManager = productLayoutManger
            adapter = productAdapter
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
        @JvmStatic
        fun newInstance(category: String) =
            CategoryFragment(category)
    }


}