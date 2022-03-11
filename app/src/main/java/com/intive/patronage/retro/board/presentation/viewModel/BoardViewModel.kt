package com.intive.patronage.retro.board.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.model.service.AddBoard

class BoardViewModel(private val repo: BoardRepository, private val addBoard: AddBoard) : ViewModel() {

    fun takeBoards() = repo.getBoards()

    fun addBoard(name: String, onSuccess: () -> Unit, onError: (text: String) -> Unit) {
        addBoard.addBoard(name, onSuccess, onError)
    }
}
