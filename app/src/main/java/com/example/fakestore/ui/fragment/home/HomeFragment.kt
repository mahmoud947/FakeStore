package com.example.fakestore.ui.fragment.home

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.FragmentHomeBinding
import com.example.fakestore.ui.fragment.home.adapters.ProductAdapter
import com.example.fakestore.ui.uiModel.News
import com.example.fakestore.utils.recyclerview.WrapContentLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    layoutId = R.layout.fragment_home,
    viewModelClass = HomeViewModel::class.java,
    isSharedViewModel = false
) {
    lateinit var menAdapter: ProductAdapter
    lateinit var electronicsAdapter: ProductAdapter
    lateinit var womenAdapter: ProductAdapter
    lateinit var jeweleryAdapter: ProductAdapter

    private lateinit var manlayoutManager: LinearLayoutManager
    private lateinit var electronicslayoutManager: LinearLayoutManager
    private lateinit var womenlayoutManager: LinearLayoutManager
    private lateinit var jewelerylayoutManager: LinearLayoutManager

    private lateinit var menuHost: MenuHost
    private lateinit var navController:NavController


    override fun onInitDataBinding() {
        navController = findNavController()
         setHasOptionsMenu(true)
       // menuHost = requireActivity()
        menAdapter = ProductAdapter(interaction = object :ProductAdapter.Interaction{
            override fun onItemSelected(position: Int, item: Product) {
                navController.navigate(HomeFragmentDirections.actionHomeToSearch())
            }

        })
        womenAdapter = ProductAdapter()
        jeweleryAdapter = ProductAdapter()
        electronicsAdapter = ProductAdapter()




        manlayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_search_icon -> navController.navigate(HomeFragmentDirections.actionHomeToSearch())
        }

        return true
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
//        val searchItem = menu.findItem(R.id.toolbar_search_with_action)
//        val searchView = searchItem.actionView as SearchView
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                if (!p0.toString().isEmpty() || p0.toString().equals("")) {
//
//                }
//                return false
//            }
//        })

    }


}