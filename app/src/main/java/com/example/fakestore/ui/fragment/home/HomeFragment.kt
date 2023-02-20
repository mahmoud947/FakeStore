package com.example.fakestore.ui.fragment.home

import android.view.*
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
    viewModelClass = HomeViewModel::class.java,
    layoutId = R.layout.fragment_home,
    isSharedViewModel = true
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
    private lateinit var navController: NavController


    override fun onInitDataBinding() {
        navController = findNavController()
        setHasOptionsMenu(true)
        // menuHost = requireActivity()

        menAdapter = ProductAdapter(interaction = object : ProductAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Product) {
                //Toast.makeText(requireContext(),item.id.toString(),Toast.LENGTH_LONG).show()
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(item.id,item.title))
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

//        binding.collectionNews.news = News(title = "New Collection", image = R.drawable.collection)
//        binding.streetClothesNews.news =
//            News(title = "Street clothes", image = R.drawable.street_clothes)
//        binding.fashionNews.news = News(title = "Fashion sale", image = R.drawable.fashion)
//        binding.blackNews.news = News(title = "Diamond in black", image = R.drawable.black)


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

        viewModel.categoryMap.observe(viewLifecycleOwner) { dataState ->
            menAdapter.isLoading = dataState.isLoading()
            womenAdapter.isLoading = dataState.isLoading()
            jeweleryAdapter.isLoading = dataState.isLoading()
            electronicsAdapter.isLoading = dataState.isLoading()

            if (!dataState.isLoading()){
                binding.collectionNews.gonView.visibility = View.GONE
                binding.streetClothesNews.gonView.visibility = View.GONE
                binding.fashionNews.gonView.visibility = View.GONE
                binding.blackNews.gonView.visibility = View.GONE
            }

            dataState.getData()?.let { mapOfStringAndMapOfStringListOfProducts ->
                val menModel = mapOfStringAndMapOfStringListOfProducts["mens-shirts"]
                val womenModel = mapOfStringAndMapOfStringListOfProducts["womens-dresses"]
                val jeweleryModel = mapOfStringAndMapOfStringListOfProducts["womens-jewellery"]
                val electronicsModel = mapOfStringAndMapOfStringListOfProducts["laptops"]



                binding.collectionNews.news =
                    menModel?.url?.let { News(title = "New Collection", image = it) }
                binding.streetClothesNews.news =
                    womenModel?.url?.let { News(title = "Street clothes", image = it) }
                binding.fashionNews.news =
                    jeweleryModel?.url?.let { News(title = "Fashion sale", image = it) }
                binding.blackNews.news =
                    electronicsModel?.url?.let { News(title = "Diamond in black", image = it) }

                menAdapter.submitList(menModel?.products)
                womenAdapter.submitList(womenModel?.products)
                jeweleryAdapter.submitList(jeweleryModel?.products)
                electronicsAdapter.submitList(electronicsModel?.products)
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