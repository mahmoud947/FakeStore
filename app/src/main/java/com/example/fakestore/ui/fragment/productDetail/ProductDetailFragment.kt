package com.example.fakestore.ui.fragment.productDetail

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentProductDetailBinding
import com.example.fakestore.ui.fragment.productDetail.adapters.ProductImagesAdapter
import com.example.fakestore.utils.recyclerview.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(
    layoutId = R.layout.fragment_product_detail,
    viewModelClass = ProductDetailViewModel::class.java,
    isSharedViewModel = false
) {
    lateinit var productImagesAdapter: ProductImagesAdapter
    lateinit var productImagesLayoutManager: WrapContentLinearLayoutManager
    private val args: ProductDetailFragmentArgs by navArgs()

    //    private val productId = args.productId
    override fun onInitDataBinding() {
        productImagesAdapter = ProductImagesAdapter()
        productImagesLayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvProductImages.apply {
            adapter = productImagesAdapter
            layoutManager = productImagesLayoutManager
        }
        binding.imagesSPI.attachToRecyclerView(binding.rvProductImages)
        //Toast.makeText(requireContext(),args.productId.toString(),Toast.LENGTH_LONG).show()
        viewModel.getProduct(args.productId)
    }

    override fun onInitViewModel() {
        viewModel.product.observe(viewLifecycleOwner) { productDataState ->
            productDataState.getData()?.let { product ->
                productImagesAdapter.submitList(product.images)
                binding.product =product
            }
        }
    }


}