package com.example.fakestore.ui.fragment.home

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.FragmentHomeBinding
import com.example.fakestore.ui.uiModel.News
import com.example.fakestore.utils.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home,
    viewModelClass = HomeViewModel::class.java
) {
    lateinit var menAdapter: ProductAdapter
    lateinit var electronicsAdapter: ProductAdapter
    lateinit var womenAdapter: ProductAdapter
    lateinit var jeweleryAdapter: ProductAdapter

    private lateinit var manlayoutManager: LinearLayoutManager
    private lateinit var electronicslayoutManager: LinearLayoutManager
    private lateinit var womenlayoutManager: LinearLayoutManager
    private lateinit var jewelerylayoutManager: LinearLayoutManager
    override fun onInitDataBinding() {
        menAdapter = ProductAdapter()
        womenAdapter = ProductAdapter()
        jeweleryAdapter = ProductAdapter()
        electronicsAdapter = ProductAdapter()



        manlayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        electronicslayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        womenlayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        jewelerylayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.collectionNews.news = News(title = "New Collection", image = R.drawable.collection)
        binding.streetClothesNews.news =
            News(title = "Street clothes", image = R.drawable.street_clothes)
        binding.fashionNews.news = News(title = "Fashion sale", image = R.drawable.fashion)
        binding.blackNews.news = News(title = "Diamond in black", image = R.drawable.black)


        binding.rvElectronics.apply {
            layoutManager = electronicslayoutManager
            adapter = electronicsAdapter
        }
        binding.rvMen.apply {
            layoutManager = manlayoutManager
            adapter = menAdapter
        }
        binding.rvJewelery.apply {
            layoutManager = jewelerylayoutManager
            adapter = jeweleryAdapter
        }
        binding.rvWomen.apply {
            layoutManager = womenlayoutManager
            adapter = womenAdapter
        }


    }

    override fun onInitViewModel() {

        viewModel.manCategory.observe(this) { productDataState ->
            menAdapter.isLoading = productDataState.isLoading()
            productDataState.getData()?.let { products ->
                menAdapter.submitList(products)
            }
        }

        viewModel.womenCategory.observe(this) { productDataState ->
            womenAdapter.isLoading = productDataState.isLoading()
            productDataState.getData()?.let { products ->
                womenAdapter.submitList(products)
            }
        }

        viewModel.jeweleryCategory.observe(this) { productDataState ->
           jeweleryAdapter.isLoading = productDataState.isLoading()
            productDataState.getData()?.let { products ->
                jeweleryAdapter.submitList(products)
            }
        }

        viewModel.electronicsCategory.observe(this) { productDataState ->
            electronicsAdapter.isLoading = productDataState.isLoading()
            productDataState.getData()?.let { products ->
                electronicsAdapter.submitList(products)
            }
        }
    }

}