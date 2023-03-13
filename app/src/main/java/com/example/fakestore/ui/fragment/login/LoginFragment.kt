package com.example.fakestore.ui.fragment.login

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.example.fakestore.R
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentLoginBinding
import com.example.fakestore.ui.activitys.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    layoutId = R.layout.fragment_login,
    viewModelClass = LoginViewModel::class.java,
    isSharedViewModel = false
) {
    override fun onInitDataBinding() {
        binding.materialButton.setOnClickListener {
            if (isValidateCredentials()) {
                viewModel.login(
                    username = binding.etUsername.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            }
        }

        binding.tvSignup.setOnClickListener {
           findNavController().navigate(
               LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
           )
        }
    }

    override fun onInitViewModel() {
        viewModel.navigateToHome.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    loadingDialog.dismissDialog()
                    if (dataState.data) {
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
                is DataState.Loading -> loadingDialog.startLoadingDialog()
                is DataState.MessageError -> {
                    loadingDialog.dismissDialog()
                    showSnackBar(dataState.errorMessage)
                }
                else -> {}
            }
        }
    }

    private fun isValidateCredentials(): Boolean {
        clearErrorState()
        var isDataValid = true
        val userNameValidation =
            viewModel.isUserNameValid(userName = binding.etUsername.text.toString())
        val passwordValidation =
            viewModel.isPasswordValid(password = binding.etPassword.text.toString())
        if (userNameValidation != null) {
            binding.tilUsername.error = userNameValidation
            binding.tilUsername.isErrorEnabled = true
            isDataValid = false
        }
        if (passwordValidation != null) {
            binding.tilPassword.error = passwordValidation
            binding.tilPassword.isErrorEnabled = true
            isDataValid = false
        }
        return isDataValid
    }

    private fun clearErrorState() {
        binding.tilUsername.error = null
        binding.tilPassword.isErrorEnabled = false
        binding.tilUsername.error = null
        binding.tilPassword.isErrorEnabled = false

    }

}