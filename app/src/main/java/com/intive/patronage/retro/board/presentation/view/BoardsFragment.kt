package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.R
import com.intive.patronage.retro.auth.model.service.Token
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.databinding.BoardFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardsFragment : Fragment() {

    private val boardViewModel: BoardViewModel by viewModel()
    private val token: Token by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = BoardFragmentBinding.inflate(inflater, container, false)
        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.show()
        uploadBoards(binding)

        return binding.root
    }

    private fun uploadBoards(binding: BoardFragmentBinding) {
        token.observe(viewLifecycleOwner) {
            isTokenGenerated ->
            if (isTokenGenerated) {
                binding.boardRv.adapter = BoardRecyclerAdapter(boardViewModel.takeBoards())
            }
        }
    }
}
