package com.intive.patronage.retro.retro.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.intive.patronage.retro.databinding.RetroDialogFragmentBinding

class RetroDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: RetroDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = RetroDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
