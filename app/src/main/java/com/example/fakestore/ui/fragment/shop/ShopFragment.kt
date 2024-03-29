package com.example.fakestore.ui.fragment.shop

import com.example.fakestore.R
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentShopBinding
import com.example.fakestore.ui.fragment.search.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : BaseFragment<FragmentShopBinding, ShopViewModel>(
    layoutId = R.layout.fragment_shop,
    viewModelClass = ShopViewModel::class.java,
    isSharedViewModel = true
) {
    lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onInitDataBinding() {
        viewPagerAdapter = ViewPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle,
        )
        binding.vpCategory.adapter = viewPagerAdapter
    }

    override fun onInitViewModel() {

        viewModel.categories.observe(viewLifecycleOwner) { categoriesDataSate ->
            when (categoriesDataSate) {
                is DataState.Success -> {
                    categoriesDataSate.data.let {
                        viewPagerAdapter.submitData(it)
                        TabLayoutMediator(binding.tabLayout, binding.vpCategory) { tab, postion ->
                            tab.text = it[postion]
                        }.attach()
                    }
                }
                is DataState.Error -> {

                }
                else -> {}
            }
        }
    }


}