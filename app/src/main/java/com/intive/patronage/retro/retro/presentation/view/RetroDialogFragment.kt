package com.intive.patronage.retro.retro.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.databinding.RetroDialogFragmentBinding
import com.intive.patronage.retro.retro.presentation.viewModel.RetroViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetroDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: RetroDialogFragmentBinding
    private val viewModel: RetroViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = RetroDialogFragmentBinding.inflate(inflater, container, false)
        binding.addNewCardButton.setOnClickListener {
            viewModel.addCards(243, 0, "hello column1").observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.i("WORKS", "works")
                    }
                    Status.ERROR -> {
                        // TODO not implemented yet
                    }
                    Status.LOADING -> {
                    }
                }
            }
        }

        return binding.root
    }
}
