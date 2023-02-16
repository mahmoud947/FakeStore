package com.example.fakestore.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseActivity
import com.example.fakestore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    layoutId = R.layout.activity_main,
    viewModelClass = MainViewModel::class.java
) {
    val testAdapter = TestAdapter()
    override fun onInitView() {
        binding.rv.adapter=testAdapter
    }

    override fun onInitViewModel() {
        viewModel.products.observe(this) {productDataState->
            productDataState.getData()?.let { products ->
                testAdapter.submitList(products)
            }
        }
    }

}