package com.example.fakestore.ui.fragment.signUp

import android.content.Intent
import androidx.navigation.fragment.findNavController
import com.example.fakestore.R
import com.example.fakestore.core.data.DataState
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentSignUpBinding
import com.example.fakestore.ui.activitys.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>(
    layoutId = R.layout.fragment_sign_up,
    viewModelClass = SignUpViewModel::class.java,
    isSharedViewModel = false
) {
    override fun onInitDataBinding() {
        binding.btnSignup.setOnClickListener {
            if (isValidateCredentials()) {
                viewModel.signUp(
                    binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            }
        }
    }

    override fun onInitViewModel() {
        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    errorDialog.dismissDialog()
                }
                else -> {
                    errorDialog.startErrorDialog()
                }
            }
        }
    }


    private fun isValidateCredentials(): Boolean {
        clearErrorState()
        var isDataValid = true
        val userNameValidation =
            viewModel.isUserNameValid(userName = binding.etEmail.text.toString())
        val passwordValidation =
            viewModel.isPasswordValid(password = binding.etPassword.text.toString())
        if (userNameValidation != null) {
            binding.tilEmail.error = userNameValidation
            binding.tilEmail.isErrorEnabled = true
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
        binding.tilEmail.error = null
        binding.tilPassword.isErrorEnabled = false
        binding.tilEmail.error = null
        binding.tilPassword.isErrorEnabled = false

    }
}