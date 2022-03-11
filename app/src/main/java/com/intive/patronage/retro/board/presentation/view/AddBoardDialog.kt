package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.databinding.AddBoardDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddBoardDialog : BottomSheetDialogFragment() {
    private lateinit var boardNameButton: MaterialButton
    private lateinit var boardNameInput: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: AddBoardDialogBinding

    private val boardViewModel: BoardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = AddBoardDialogBinding.inflate(inflater, container, false)
        boardNameButton = binding.addNewBoardButton
        progressBar = binding.progressBarCircular
        boardNameInput = binding.newBoardName

        showButton(isVisible = true, isEnabled = false, isClickable = false)
        showProgressBar("gone")
        showBoardInput(true, "Board name")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        boardNameInput.editText?.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.trim().isEmpty() || text.length < 4 || text.length > 30) {
                    showButton(isVisible = true, isEnabled = false, isClickable = false)
                } else {
                    showButton(isVisible = true, isEnabled = true, isClickable = true)
                }
            }
        }

        boardNameButton.setOnClickListener {
            showProgressBar("visible")
            showBoardInput(false)
            showButton(isVisible = false, isEnabled = false, isClickable = false)

            val inputText = boardNameInput.editText?.text.toString()
            boardViewModel.addBoard(inputText, ::onSuccess, ::onError)
        }
    }

    private fun showButton(isVisible: Boolean, isEnabled: Boolean, isClickable: Boolean) {
        boardNameButton.isVisible = isVisible
        boardNameButton.isEnabled = isEnabled
        boardNameButton.isClickable = isClickable
    }

    private fun showProgressBar(state: String) {
        when (state) {
            "visible" -> {
                progressBar.visibility = ProgressBar.VISIBLE
            }
            "gone" -> {
                progressBar.visibility = ProgressBar.GONE
            }
            "invisible" -> {
                progressBar.visibility = ProgressBar.INVISIBLE
            }
            else -> progressBar.visibility = ProgressBar.VISIBLE
        }
    }

    private fun showBoardInput(isEnabled: Boolean, label: String = "", cleanText: Boolean = false) {
        if (isEnabled) {
            if (cleanText) {
                boardNameInput.editText?.setText("")
            }
            boardNameInput.hint = label
            boardNameInput.editText?.isEnabled = isEnabled
        } else {
            boardNameInput.hint = null
            boardNameInput.editText?.isEnabled = isEnabled
        }
    }

    private fun onError(label: String) {
        showProgressBar("invisible")

        Snackbar.make(binding.root, label, Snackbar.LENGTH_SHORT)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(
                    transientBottomBar: Snackbar?,
                    event: Int,
                ) {
                    super.onDismissed(transientBottomBar, event)
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        showProgressBar("gone")
                        showBoardInput(true, "Board name")
                        showButton(isVisible = true, isEnabled = true, isClickable = true)
                    }
                }
            }).show()
    }

    private fun onSuccess() {
        if (this.isVisible) {
            val action = AddBoardDialogDirections.actionAddBoardDialogToBoardsFragment()
            findNavController().navigate(action)
            findNavController().popBackStack()
        }
    }
}
