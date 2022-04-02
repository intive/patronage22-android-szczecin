package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.common.helpers.softKeyboardHandler
import com.intive.patronage.retro.databinding.AddUserDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddUserDialog : BottomSheetDialogFragment() {
    private lateinit var userNameButton: MaterialButton
    private lateinit var userNameInput: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: AddUserDialogBinding
    private val args: AddUserDialogArgs by navArgs()

    private val boardViewModel: BoardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = AddUserDialogBinding.inflate(inflater, container, false)
        userNameButton = binding.addNewUserButton
        progressBar = binding.progressBarCircular
        userNameInput = binding.newUserName

        showButton(isVisible = true, isEnabled = false, isClickable = false)
        showProgressBar("gone")
        showBoardInput(true, "User name")
        softKeyboardHandler(dialog)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userNameInput.editText?.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if (text.trim().isEmpty()) {
                    showButton(isVisible = true, isEnabled = false, isClickable = false)
                } else {
                    showButton(isVisible = true, isEnabled = true, isClickable = true)
                }
            }
        }

        userNameButton.setOnClickListener {
            val inputText = userNameInput.editText?.text.toString()
            boardViewModel.addUsers(args.boardId, listOf(inputText)).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        dismiss()
                    }
                    Status.ERROR -> {
                        onError("Something went wrong")
                    }
                    Status.LOADING -> {
                        showProgressBar("visible")
                        showBoardInput(false)
                        showButton(isVisible = false, isEnabled = false, isClickable = false)
                    }
                }
            }
        }
    }

    private fun showButton(isVisible: Boolean, isEnabled: Boolean, isClickable: Boolean) {
        userNameButton.isVisible = isVisible
        userNameButton.isEnabled = isEnabled
        userNameButton.isClickable = isClickable
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
                userNameInput.editText?.setText("")
            }
            userNameInput.hint = label
            userNameInput.editText?.isEnabled = isEnabled
        } else {
            userNameInput.hint = null
            userNameInput.editText?.isEnabled = isEnabled
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
}
