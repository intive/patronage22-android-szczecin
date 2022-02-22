package com.intive.patronage.retro.board.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.intive.patronage.retro.board.model.repo.BoardRepository

class BoardViewModel(private val repo: BoardRepository) : ViewModel() {

    fun takeBoards() = repo.getBoards()
}
