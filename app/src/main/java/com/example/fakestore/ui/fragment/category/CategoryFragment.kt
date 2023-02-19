package com.example.fakestore.ui.fragment.category

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentCategoryBinding
import com.example.fakestore.ui.fragment.category.adapters.CategoryAdapter
import com.example.fakestore.utils.recyclerview.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment(
    private val category: String = ""
) : BaseFragment<FragmentCategoryBinding, CategoryViewModel>(
    layoutId = R.layout.fragment_category,
    viewModelClass = CategoryViewModel::class.java,
    isSharedViewModel = false
) {
    private lateinit var productAdapter: CategoryAdapter
    private lateinit var productLayoutManger: WrapContentLinearLayoutManager


    override fun onInitDataBinding() {
        productAdapter = CategoryAdapter()
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