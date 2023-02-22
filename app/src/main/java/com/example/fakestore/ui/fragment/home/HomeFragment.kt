package com.example.fakestore.ui.fragment.home

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.FragmentHomeBinding
import com.example.fakestore.ui.fragment.home.adapters.HeaderFooterAdapter
import com.example.fakestore.ui.fragment.home.adapters.PagingProductAdapter
import com.example.fakestore.ui.fragment.home.adapters.ProductAdapter
import com.example.fakestore.ui.uiModel.News
import com.example.fakestore.utils.recyclerview.AutoFitGridLayoutManager
import com.example.fakestore.utils.recyclerview.WrapAutoFitGridlayoutManager
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
    lateinit var pagingProductAdapter: PagingProductAdapter

    private lateinit var manlayoutManager: LinearLayoutManager
    private lateinit var electronicslayoutManager: LinearLayoutManager
    private lateinit var womenlayoutManager: LinearLayoutManager
    private lateinit var jewelerylayoutManager: LinearLayoutManager
    private lateinit var pagingProductLayoutManager: WrapAutoFitGridlayoutManager

    private lateinit var menuHost: MenuHost
    private lateinit var navController: NavController
    private lateinit var dialog: AlertDialog

    override fun onInitDataBinding() {
        navController = findNavController()
        setHasOptionsMenu(true)
        // menuHost = requireActivity()

        val onItemClicked = object : ProductAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Product) {
                navController.navigate(
                    HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                        item.id, item.title
                    )
                )
            }

            override fun onAddToFavoriteClick(item: Product) {
                Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
                viewModel.addProductTOFavorite(item)
            }
        }
        menAdapter = ProductAdapter(interaction = onItemClicked)
        womenAdapter = ProductAdapter(interaction = onItemClicked)
        jeweleryAdapter = ProductAdapter(interaction = onItemClicked)
        electronicsAdapter = ProductAdapter(interaction = onItemClicked)
        pagingProductAdapter =
            PagingProductAdapter(onItemClicked = object : PagingProductAdapter.OnItemClicked {
                override fun onItemClicked(product: Product, oldPosition: Int) {
                    findNavController().navigate(
                        HomeFragmentDirections.toDetailScreen(
                            product.id,
                            product.title
                        )
                    )
                }
            })
        pagingProductLayoutManager = WrapAutoFitGridlayoutManager(requireContext(), 400)

        pagingProductLayoutManager.spanSizeLookup =  object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == pagingProductAdapter.itemCount && pagingProductAdapter.itemCount > 0) {
                    pagingProductLayoutManager.spanCount
                } else {
                    1
                }
            }
        }



        manlayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        electronicslayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        womenlayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        jewelerylayoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        pagingProductAdapter.addLoadStateListener {
            when (it.append) {
                is LoadState.Error -> errorDialog.startErrorDialog()
                else -> {
                    errorDialog.dismissDialog()
                }
            }
            when (it.refresh) {
                is LoadState.Error -> errorDialog.startErrorDialog()
                else -> {
                    errorDialog.dismissDialog()
                }
            }
        }

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
        binding.rvPagingProducts.apply {
            setHasFixedSize(true)
            layoutManager = pagingProductLayoutManager
            adapter = pagingProductAdapter.withLoadStateFooter(HeaderFooterAdapter(object :HeaderFooterAdapter.OnRetryClicked{
                override fun onRetryClicked() {
                    pagingProductAdapter.retry()
                }

            }))
        }

    }


    override fun onInitViewModel() {

        viewModel.categoryMap.observe(viewLifecycleOwner) { dataState ->
            menAdapter.isLoading = dataState.isLoading()
            womenAdapter.isLoading = dataState.isLoading()
            jeweleryAdapter.isLoading = dataState.isLoading()
            electronicsAdapter.isLoading = dataState.isLoading()

            if (!dataState.isLoading()) {
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

        viewModel.pagingProducts.observe(viewLifecycleOwner){
            pagingProductAdapter.submitData(this.lifecycle,it)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_search_icon -> navController.navigate(HomeFragmentDirections.actionHomeToSearch())
//            R.id.toolbar_search_icon->{
//
//                val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity(), R.style.WrapContentDialog)
//                val view: View? = requireActivity().layoutInflater.inflate(R.layout.test_dialog, null)
//                builder.setView(view)
//                builder.setCancelable(false)
//                dialog = builder.create()
//
//                dialog.show()
//            }

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