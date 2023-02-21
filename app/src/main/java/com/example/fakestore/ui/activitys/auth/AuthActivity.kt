package com.example.fakestore.ui.activitys.auth

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseActivity
import com.example.fakestore.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>(
    layoutId = R.layout.activity_auth,
    viewModelClass = AuthViewModel::class.java
) {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onInitView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerViewAuth) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onInitViewModel() {

    }

}