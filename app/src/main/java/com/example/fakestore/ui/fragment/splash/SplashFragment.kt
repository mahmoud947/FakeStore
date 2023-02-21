package com.example.fakestore.ui.fragment.splash

import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.fakestore.R
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentSplashBinding
import com.example.fakestore.ui.activitys.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(
        layoutId = R.layout.fragment_splash,
        viewModelClass = SplashViewModel::class.java,
        isSharedViewModel = false
    ) {
    override fun onInitDataBinding() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.splash_logo_anim)
        binding.splashLogoAnim = anim
    }

    override fun onInitViewModel() {
        viewModel.navigateToMain.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                AuthState.AUTHENTICATED -> {
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                AuthState.UNAUTHENTICATED -> {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }
            }
        }
    }

}