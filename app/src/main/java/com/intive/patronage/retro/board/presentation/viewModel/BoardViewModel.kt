package com.intive.patronage.retro.board.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.common.api.Resource
import kotlinx.coroutines.Dispatchers

class BoardViewModel(private val repo: BoardRepository) : ViewModel() {

    fun getBoards() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getBoards())
    }

    fun addBoard(boardName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.addBoard(Board(0, "", boardName, 0)))
    }

    fun addUsers(id: Int, users: List<String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.addUsers(id, users))
    }
}
