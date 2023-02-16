package com.example.fakestore.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseActivity
import com.example.fakestore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(
    layoutId = R.layout.activity_main,
    viewModelClass = MainViewModel::class.java
) {
    override fun onInitView() {

    }

    override fun onInitViewModel() {

    }

}