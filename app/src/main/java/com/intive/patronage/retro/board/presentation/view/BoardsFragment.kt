package com.intive.patronage.retro.board.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.intive.patronage.retro.R
import com.intive.patronage.retro.auth.model.service.Token
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.databinding.BoardFragmentBinding
import com.intive.patronage.retro.main.presentation.view.MainActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardsFragment : Fragment() {

    private val boardViewModel: BoardViewModel by viewModel()
    private val token: Token by inject()
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

    private fun uploadBoards(binding: BoardFragmentBinding) {
        token.observe(viewLifecycleOwner) {
            isTokenGenerated ->
            if (isTokenGenerated) {
                boardViewModel.boards.observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.SUCCESS -> binding.boardRv.adapter = BoardRecyclerAdapter(it.data!!)
                        Status.ERROR -> Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                        Status.LOADING -> Toast.makeText(this.context, "LOADING...", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        uploadBoards(binding)
    }
}
