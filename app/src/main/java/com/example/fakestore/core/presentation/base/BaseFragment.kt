package com.example.fakestore.core.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fakestore.utils.dialogs.ErrorDialog
import com.example.fakestore.utils.dialogs.LoadingDialog



abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes
    private val layoutId: Int,
    private val viewModelClass: Class<VM>,
    private val isSharedViewModel:Boolean
) : Fragment() {
    lateinit var viewModel: VM
    lateinit var binding: DB

    lateinit var loadingDialog: LoadingDialog
    lateinit var errorDialog: ErrorDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSharedViewModel) {
            viewModel = ViewModelProvider(requireActivity())[viewModelClass]
        } else {
            viewModel = ViewModelProvider(this)[viewModelClass]
        }


    }

    abstract fun onInitDataBinding()
    abstract fun onInitViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingDialog = LoadingDialog(requireActivity())
        errorDialog = ErrorDialog(activity = requireActivity(), buttonTitle = "Retry")
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        onInitDataBinding()
        onInitViewModel()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()


    }

    override fun onDestroy() {
        super.onDestroy()


    }


}