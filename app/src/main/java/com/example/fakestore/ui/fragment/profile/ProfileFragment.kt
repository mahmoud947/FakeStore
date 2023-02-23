package com.example.fakestore.ui.fragment.profile

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.fakestore.R
import com.example.fakestore.core.data.getData
import com.example.fakestore.core.presentation.base.BaseFragment
import com.example.fakestore.databinding.FragmentProfileBinding
import com.example.fakestore.utils.extensions.setImageFromUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    layoutId = R.layout.fragment_profile,
    viewModelClass = ProfileViewModel::class.java,
    isSharedViewModel = false
) {

    private lateinit var profileImageView: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvFullName: TextView

    override fun onInitDataBinding() {
        profileImageView = requireActivity().findViewById(R.id.profile_image)
        tvUserName = requireActivity().findViewById(R.id.tv_user_name)
        tvFullName = requireActivity().findViewById(R.id.tv_full_name)
    }

    @SuppressLint("SetTextI18n")
    override fun onInitViewModel() {
        viewModel.user.observe(viewLifecycleOwner) { userDataState ->
            userDataState.getData()?.let {
                tvUserName.text = it.username
                tvFullName.text = "${it.firstName}  ${it.lastName}"
                profileImageView.setImageFromUrl(it.image)
                findNavController().currentDestination?.label = it.username
            }
        }
    }
}