package com.intive.patronage.retro.board.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
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
    private val boardsNavigator: BoardsNavigator by inject()
    lateinit var binding: BoardFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        boardsNavigator.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = BoardFragmentBinding.inflate(inflater, container, false)
        val fab = (activity as MainActivity).binding.floatingButton

        binding.boardRecyclerAdapter = BoardRecyclerAdapter()

        retroViewModel.stopHeartBeat()
        fab.show()
        fab.setOnClickListener {
            val action = BoardsFragmentDirections.actionBoardsFragmentToAddBoardDialog()
            Navigation.findNavController(binding.root).navigate(action)
        }
        uploadBoards(binding)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        boardsNavigator.detach(this)
    }

    private fun uploadBoards(binding: BoardFragmentBinding) {
        authToken.getToken().observe(viewLifecycleOwner) { authToken ->
            if (authToken.isNotEmpty()) {
                boardViewModel.getBoards().observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.indicator.visibility = View.GONE
                            binding.boardRecyclerAdapter!!.uploadBoardsData(it.data!!, boardViewModel)
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
            binding.boardRecyclerView.visibility = View.GONE
            binding.noBoardsLayout.root.visibility = View.VISIBLE
        }
    }
}
