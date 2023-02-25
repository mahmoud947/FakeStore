package com.example.fakestore.ui.fragment.productDetail

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.FragmentProductDetailBinding
import com.example.fakestore.ui.fragment.productDetail.adapters.ProductImagesAdapter
import com.example.fakestore.ui.fragment.productDetail.adapters.SuggestionAdapter
import com.example.fakestore.utils.recyclerview.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(
    layoutId = R.layout.fragment_product_detail,
    viewModelClass = ProductDetailViewModel::class.java,
    isSharedViewModel = false
) {
    private lateinit var productImagesAdapter: ProductImagesAdapter
    private lateinit var productImagesLayoutManager: WrapContentLinearLayoutManager
    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var suggestionLayoutManager: WrapContentLinearLayoutManager
    private val args: ProductDetailFragmentArgs by navArgs()


    override fun onInitDataBinding() {
        productImagesAdapter = ProductImagesAdapter()
        suggestionAdapter = SuggestionAdapter(
            interaction = object : SuggestionAdapter.Interaction {
                override fun onItemSelected(position: Int, item: Product) {
                    findNavController().navigate(
                        ProductDetailFragmentDirections.toDetailScreen(
                            item.id,
                            item.title
                        )
                    )
                }
            }
        )
        productImagesLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        suggestionLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        binding.rvProductImages.apply {
            adapter = productImagesAdapter
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 4
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(20))
        compositePageTransformer.addTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                val r = 1 - Math.abs(position)
                page.scaleY = (0.85f + r * 0.15f)
            }

        })
        binding.rvProductImages.setPageTransformer(compositePageTransformer)

        binding.rvSuggestion.apply {
            adapter = suggestionAdapter
            layoutManager = suggestionLayoutManager
        }
        binding.imagesSPI.attachToPager(binding.rvProductImages)
        viewModel.getProduct(args.productId)
    }


    override fun onInitViewModel() {

        viewModel.product.observe(viewLifecycleOwner) { productDataState ->
            //productImagesAdapter.isLoading = productDataState.isLoading()
            productDataState.getData()?.let { product ->
                productImagesAdapter.submitList(product.images)
                binding.product = product
                viewModel.getProductsOfCategory(product.category)
            }
        }

        viewModel.suggestion.observe(viewLifecycleOwner) { suggestionProduct ->
            suggestionProduct.getData()?.let {
                suggestionAdapter.submitList(it)
            }

        }
    }


}