package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.intive.patronage.retro.R
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.common.helpers.softKeyboardHandler
import com.intive.patronage.retro.databinding.AddUserDialogBinding
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddUserDialog : BottomSheetDialogFragment() {
    private lateinit var binding: AddUserDialogBinding
    private var emails = ArrayList<String>()
    private val boardViewModel: BoardViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val args: AddUserDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = AddUserDialogBinding.inflate(inflater, container, false)

        binding.addNewUserButton.isEnabled = false
        binding.progressBarAddUser.visibility = View.GONE
        binding.newUserEmail.hint = getString(R.string.user_email)
        binding.newUserEmail.editText?.isEnabled = true
        softKeyboardHandler(dialog)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newUserEmail.editText?.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                binding.addNewUserButton.isEnabled = !(text.trim().isEmpty() || text.length < 5 || text.length > 255)
            }
        }

        binding.addNewUserButton.setOnClickListener {
            val inputText = binding.newUserEmail.editText?.text.toString()
            boardViewModel.getUsers(inputText).observe(viewLifecycleOwner) { getUsers ->
                when (getUsers.status) {
                    Status.SUCCESS -> {
                        with(binding) {
                            progressBarAddUser.visibility = View.GONE
                            newUserEmail.editText?.setText("")
                            newUserEmail.hint = getString(R.string.user_email)
                            newUserEmail.editText?.isEnabled = true
                            addNewUserButton.isVisible = true
                            addNewUserButton.isEnabled = false
                        }

                        emails = ArrayList()
                        for (i in 0 until binding.chipGroup.childCount) {
                            val email = (binding.chipGroup.getChildAt(i) as Chip).text.toString()
                            emails.add(email)
                        }

                        if (inputText in getUsers.data!! && inputText !in emails && inputText != mainViewModel.getEmail()) {

                            val chip = Chip(this.context)
                            chip.isCloseIconVisible = true
                            chip.isFocusable = false
                            chip.text = inputText
                            binding.chipGroup.addView(chip)
                            emails.add(inputText)
                            checkChipsGroupIsEmpty()

                            chip.setOnCloseIconClickListener {
                                binding.chipGroup.removeView(chip)
                                emails.remove(chip.text.toString())
                                checkChipsGroupIsEmpty()
                            }
                        } else {
                            onErrorAddUser(inputText, getString(R.string.error_add_user_to_board))
                        }
                    }
                    Status.ERROR -> {
                        onErrorAddUser(inputText, getUsers.message!!)
                    }
                    Status.LOADING -> {
                        binding.progressBarAddUser.visibility = View.VISIBLE
                        binding.newUserEmail.hint = null
                        binding.newUserEmail.editText?.isEnabled = false
                        binding.addNewUserButton.visibility = View.GONE
                    }
                }
            }
        }
        binding.saveNewUsersButton.setOnClickListener {
            boardViewModel.addUsers(args.boardId, emails).observe(viewLifecycleOwner) { addUsers ->
                when (addUsers.status) {
                    Status.SUCCESS -> {
                        dismiss()
                    }
                    Status.ERROR -> {
                        onErrorChips(addUsers.message!!)
                    }
                    Status.LOADING -> {
                        binding.saveNewUsersButton.visibility = View.GONE
                        binding.progressBarChips.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun onErrorAddUser(inputText: String, message: String) {
        with(binding) {
            progressBarAddUser.visibility = View.INVISIBLE
            addNewUserButton.isVisible = false
            newUserEmail.editText?.isEnabled = false
            newUserEmail.editText?.setText(inputText)
            newUserEmail.hint = ""
        }

        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(
                    transientBottomBar: Snackbar?,
                    event: Int,
                ) {
                    super.onDismissed(transientBottomBar, event)
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_SWIPE) {
                        binding.newUserEmail.hint = getString(R.string.user_email)
                        binding.newUserEmail.editText?.isEnabled = true
                        binding.progressBarAddUser.visibility = View.GONE
                        binding.addNewUserButton.isVisible = true
                    }
                }
            }).show()
    }

    private fun onErrorChips(message: String) {
        binding.progressBarChips.visibility = View.INVISIBLE

        Snackbar.make(binding.saveNewUsersButton, message, Snackbar.LENGTH_SHORT)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(
                    transientBottomBar: Snackbar?,
                    event: Int,
                ) {
                    super.onDismissed(transientBottomBar, event)
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_SWIPE) {
                        binding.progressBarChips.visibility = View.GONE
                        binding.saveNewUsersButton.visibility = View.VISIBLE
                    }
                }
            }).show()
    }

    private fun checkChipsGroupIsEmpty() {
        with(binding) {
            if (chipGroup.isNotEmpty()) {
                chipGroup.visibility = View.VISIBLE
                separatorLine.visibility = View.VISIBLE
                saveNewUsersButton.visibility = View.VISIBLE
            } else {
                chipGroup.visibility = View.GONE
                separatorLine.visibility = View.GONE
                saveNewUsersButton.visibility = View.GONE
            }
        }
    }
}
