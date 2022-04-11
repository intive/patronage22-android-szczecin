package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.intive.patronage.retro.R
import com.intive.patronage.retro.auth.model.service.AuthToken
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.databinding.BoardFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import com.intive.patronage.retro.retro.presentation.viewModel.RetroViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardsFragment : Fragment() {

    private val boardViewModel: BoardViewModel by viewModel()
    private val retroViewModel: RetroViewModel by viewModel()
    private val authToken: AuthToken by inject()
    private lateinit var binding: BoardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BoardFragmentBinding.inflate(inflater, container, false)
        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton

        retroViewModel.stopHeartBeat()
        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.show()
        fab.setOnClickListener {
            val action = BoardsFragmentDirections.actionBoardsFragmentToAddBoardDialog()
            Navigation.findNavController(binding.root).navigate(action)
        }
        uploadBoards(binding)

        return binding.root
    }

    private fun uploadBoards(binding: BoardFragmentBinding) {
        authToken.getToken().observe(viewLifecycleOwner) { authToken ->
            if (authToken.isNotEmpty()) {
                boardViewModel.getBoards().observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.indicator.visibility = View.GONE
                            binding.boardRv.adapter = BoardRecyclerAdapter(it.data!!, boardViewModel)
                            noBoardsDisplay(it.data.isEmpty())
                        }
                        Status.ERROR -> {
                            binding.indicator.visibility = View.GONE
                            Snackbar.make(binding.contextView, it.message!!, Snackbar.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> binding.indicator.isShown
                    }
                }
            }
        }
    }

    private fun noBoardsDisplay(isListEmpty: Boolean) {
        if (isListEmpty) {
            binding.boardRv.visibility = View.GONE
            binding.noBoardsLayout.root.visibility = View.VISIBLE
        }
    }
}
