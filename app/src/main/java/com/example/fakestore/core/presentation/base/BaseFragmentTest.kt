package com.example.fakestore.core.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.fakestore.utils.dialogs.ErrorDialog
import com.example.fakestore.utils.dialogs.LoadingDialog

abstract class BaseFragmentTest<DB : ViewDataBinding, VM : BaseViewModel?>() : Fragment() {
    lateinit var binding: DB
    lateinit var loadingDialog: LoadingDialog
    lateinit var errorDialog: ErrorDialog

    abstract fun initDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): DB

    abstract val viewModel:VM?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    abstract fun onInitView()
    abstract fun onInitViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initDataBinding(inflater, container, savedInstanceState)
        loadingDialog = LoadingDialog(requireActivity())
        errorDialog = ErrorDialog(activity = requireActivity(), buttonTitle = "Retry")
        binding.lifecycleOwner = viewLifecycleOwner

        onInitView()
       // onInitViewModel()

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