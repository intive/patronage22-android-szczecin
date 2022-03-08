package com.intive.patronage.retro.board.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.presentation.entity.Board

class BoardViewModel(private val repo: BoardRepository) : ViewModel() {

    val boards: LiveData<List<Board>> = liveData {
        emit(repo.getBoards())
    }
}
