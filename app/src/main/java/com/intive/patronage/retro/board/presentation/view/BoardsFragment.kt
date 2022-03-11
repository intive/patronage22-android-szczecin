package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.intive.patronage.retro.R
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.databinding.BoardFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardsFragment : Fragment() {

    private val boardViewModel: BoardViewModel by viewModel()

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
        fab.setOnClickListener {
            val action = BoardsFragmentDirections.actionBoardsFragmentToAddBoardDialog()
            Navigation.findNavController(binding.root).navigate(action)
        }
        uploadBoards(binding)

        return binding.root
    }

    private fun uploadBoards(binding: BoardFragmentBinding) {
        val boardAdapter = BoardRecyclerAdapter(boardViewModel.takeBoards())
        binding.boardRv.adapter = boardAdapter
    }
}
