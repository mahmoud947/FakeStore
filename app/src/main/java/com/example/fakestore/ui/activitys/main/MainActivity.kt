package com.example.fakestore.ui.activitys.main

import android.graphics.Color
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseActivity
import com.example.fakestore.databinding.ActivityMainBinding
import com.example.fakestore.ui.fragment.home.HomeFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    layoutId = R.layout.activity_main,
    viewModelClass = MainViewModel::class.java
) {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onInitView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.shopFragment
            )
        )
        binding.navigationView.setupWithNavController(navController)
        binding.collapsingToolBarLayout.setExpandedTitleColor(Color.TRANSPARENT)
        binding.toolBar.setupWithNavController(navController, appBarConfiguration)

        binding.toolBar.setNavigationOnClickListener { _ ->
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }
        binding.collapsingToolBarLayout.setupWithNavController(
            binding.toolBar,
            navController,
            appBarConfiguration
        )


        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.tvCbTitle.text = destination.label
        }

        setSupportActionBar(binding.toolBar)
        setupActionBarWithNavController(navController, appBarConfiguration)


//        binding.navigationView.setOnItemSelectedListener { item ->
//           when(item.itemId){
//               R.id.homeFragment->supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id,HomeFragment()).commit()
//           }
//            false
//        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun onInitViewModel() {

    }

}