package com.intive.patronage.retro.retro.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.databinding.RetroDialogFragmentBinding
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import com.intive.patronage.retro.retro.presentation.viewModel.RetroViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetroDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: RetroDialogFragmentBinding
    private val viewModel: RetroViewModel by viewModel()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val args: RetroDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = RetroDialogFragmentBinding.inflate(inflater, container, false)
        binding.retroProgressBarCircular.visibility = View.GONE

        buttonListener()

        return binding.root
    }

    private fun buttonListener() {
        binding.addNewCardButton.setOnClickListener {
            val text = binding.newRetroName.editText?.text.toString()

            viewModel.addCards(args.boardId, args.columnId, text).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.retroProgressBarCircular.visibility = View.GONE
                        mainViewModel.isDialogClosed.postValue(true)
                        dismiss()
                    }
                    Status.ERROR -> {
                        binding.retroProgressBarCircular.visibility = View.GONE
                        Snackbar.make(binding.retroDialogContextView, it.message!!, Snackbar.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        binding.retroProgressBarCircular.visibility = View.VISIBLE
                        binding.retroProgressBarCircular.isShown
                    }
                }
            }
        }
    }
}
