package com.example.fakestore.ui.fragment.search

import android.text.TextUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.FragmentSearchBinding
import com.example.fakestore.ui.fragment.search.adapters.SearchAdapter
import com.example.fakestore.utils.extensions.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    layoutId = R.layout.fragment_search,
    viewModelClass = SearchViewModel::class.java,
    isSharedViewModel = false

) {
    lateinit var searchAdapter: SearchAdapter
    lateinit var searchLayoutManager: LinearLayoutManager
    override fun onInitDataBinding() {
        binding.viewModel = viewModel
        searchAdapter = SearchAdapter(interaction = object : SearchAdapter.Interaction {
            override fun onItemSelected(position: Int, item: Product) {
                findNavController().navigate(
                    SearchFragmentDirections.toDetailScreen(
                        item.id,
                        item.title
                    )
                )
            }
        })
        searchLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rvSearchResult.apply {
            layoutManager = searchLayoutManager
            adapter = searchAdapter
        }

        binding.edSearch.textChanges().debounce(600).onEach {
            if (!TextUtils.isEmpty(it)) {
                viewModel.search(it.toString())
            } else {
                searchAdapter.submitList(emptyList())
            }

        }.launchIn(this.lifecycleScope)

    }

    override fun onInitViewModel() {
        viewModel.searchResult.observe(this) { searchResultDataState ->
            searchResultDataState.getData()?.let {
                searchAdapter.submitList(it)
            }
        }
    }


}