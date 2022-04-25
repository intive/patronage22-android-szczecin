package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddUserDialog : BottomSheetDialogFragment() {
    private lateinit var binding: AddUserDialogBinding
    private val boardViewModel: BoardViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val args: AddUserDialogArgs by navArgs()
    private var emails = ArrayList<String>()

    private val EditText.textFlow: Flow<String>
        get() = callbackFlow {
            val textWatcher = doAfterTextChanged { trySend(it.toString()).isSuccess }
            awaitClose { removeTextChangedListener(textWatcher) }
        }

    private fun View.hideKeyboard() {
        val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = AddUserDialogBinding.inflate(inflater, container, false)

        with(binding) {
            addNewUsersButton.isEnabled = false
            progressBarAddUser.visibility = View.GONE
            newUserEmail.hint = getString(R.string.user_email)
            newUserEmail.editText?.isEnabled = true
            chipGroup.visibility = View.GONE
        }
        softKeyboardHandler(dialog)

        return binding.root
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flow = binding.newUserEmail.editText?.textFlow
            ?.debounce(100)
            ?.filter { query ->
                with(binding) {
                    if (query.length > 2) {
                        separatorLine.visibility = View.VISIBLE
                        chipGroup.visibility = View.GONE
                        textView.visibility = View.VISIBLE
                        addEmailButton.visibility = View.VISIBLE
                        addEmailButton.isEnabled = false
                        addNewUsersButton.visibility = View.GONE

                        return@filter true
                    } else {
                        textView.text = getString(R.string.searching)
                        addEmailButton.visibility = View.GONE
                        textView.visibility = View.GONE
                        addNewUsersButton.visibility = View.VISIBLE

                        if (chipGroup.isEmpty()) {
                            chipGroup.visibility = View.GONE
                            separatorLine.visibility = View.GONE
                        } else {
                            chipGroup.visibility = View.VISIBLE
                            separatorLine.visibility = View.VISIBLE
                        }
                        return@filter false
                    }
                }
            }
            ?.flatMapLatest { query ->
                boardViewModel.getUsers(query)
                    .catch {
                        emitAll(flowOf(emptyList()))
                    }
            }?.flowOn(Dispatchers.Main)

        CoroutineScope(Dispatchers.Main).launch {
            flow?.collect { result ->
                if (result.isNotEmpty() && result[0] == "") {
                    Snackbar.make(binding.root, getString(R.string.error_add_user_to_board), Snackbar.LENGTH_SHORT).show()
                } else {
                    val emailEditText = binding.newUserEmail.editText?.text.toString()
                    var emails = mutableListOf<String>()

                    for (i in result.indices) {
                        if (result[i].length >= emailEditText.length && emailEditText == result[i].substring(0, emailEditText.length)) {
                            emails.add(result[i])
                        }
                    }

                    if (emails.isEmpty()) {
                        binding.textView.text = getString(R.string.no_matching_results)
                    } else {
                        if (emails.size > 8) {
                            emails = emails.subList(0, 8)
                        }

                        var emailsTextView = ""
                        for (email in emails) {
                            emailsTextView += "$email\n"
                        }
                        emailsTextView = emailsTextView.dropLast(1)
                        binding.textView.text = emailsTextView
                        binding.addEmailButton.isEnabled = emailEditText in emails
                    }
                }
            }
        }

        binding.addEmailButton.setOnClickListener {
            val inputText = binding.newUserEmail.editText?.text.toString()
            emails = ArrayList()

            for (i in 0 until binding.chipGroup.childCount) {
                val email = (binding.chipGroup.getChildAt(i) as Chip).text.toString()
                emails.add(email)
            }

            if (inputText !in emails && inputText != mainViewModel.getEmail()) {
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
                binding.root.hideKeyboard()
                binding.newUserEmail.editText?.text = null
            } else {
                Snackbar.make(binding.root, getString(R.string.error_add_user_to_board), Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.addNewUsersButton.setOnClickListener {
            boardViewModel.addUsers(args.boardId, emails).observe(viewLifecycleOwner) { addUsers ->
                when (addUsers.status) {
                    Status.SUCCESS -> {
                        dismiss()
                    }
                    Status.ERROR -> {
                        onErrorAddUsers(addUsers.message!!)
                    }
                    Status.LOADING -> {
                        binding.addNewUsersButton.visibility = View.GONE
                        binding.progressBarAddUser.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun checkChipsGroupIsEmpty() {
        with(binding) {
            if (chipGroup.isNotEmpty()) {
                addNewUsersButton.isEnabled = true
                separatorLine.visibility = View.VISIBLE
            } else {
                addNewUsersButton.isEnabled = false
                separatorLine.visibility = View.GONE
                chipGroup.visibility = View.GONE
            }
        }
    }

    private fun onErrorAddUsers(message: String) {
        with(binding) {
            progressBarAddUser.visibility = View.INVISIBLE
            addNewUsersButton.isVisible = true
            addNewUsersButton.isVisible = false
            newUserEmail.editText?.isEnabled = false
        }

        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(
                    transientBottomBar: Snackbar?,
                    event: Int,
                ) {
                    super.onDismissed(transientBottomBar, event)
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_SWIPE) {
                        binding.newUserEmail.editText?.isEnabled = true
                        binding.progressBarAddUser.visibility = View.GONE
                        binding.addNewUsersButton.isVisible = true
                    }
                }
            }).show()
    }
}
