package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.R
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.databinding.BoardFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardsFragment : Fragment() {

    private val boardViewModel: BoardViewModel by viewModel()
    private lateinit var binding: BoardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BoardFragmentBinding.inflate(inflater, container, false)
        val bottomAppBar = (activity as MainActivity).binding.bottomAppBar
        val fab = (activity as MainActivity).binding.floatingButton

        bottomAppBar.replaceMenu(R.menu.bottom_app_bar_menu_boards)
        fab.show()

        return binding.root
    }

    private fun uploadBoards(binding: BoardFragmentBinding, list: List<Board>) {
        val boardAdapter = BoardRecyclerAdapter(list)
        binding.boardRv.adapter = boardAdapter
    }

    override fun onStart() {
        super.onStart()
        boardViewModel.boards.observe(viewLifecycleOwner) {
            uploadBoards(binding, it)
            Log.i("IT WORKS !!! ", it.size.toString())
            Log.i("IT WORKS !!! ", it.get(0).name)
        }
    }
}
