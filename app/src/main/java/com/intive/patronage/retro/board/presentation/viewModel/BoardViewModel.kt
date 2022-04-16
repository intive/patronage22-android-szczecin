package com.intive.patronage.retro.board.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.intive.patronage.retro.board.model.entity.BoardUpdateRemote
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.board.presentation.view.BoardsNavigator
import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoardViewModel(private val repo: BoardRepository, private val boardsNavigator: BoardsNavigator) : ViewModel() {

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

    fun editBoard(id: Int, name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.updateBoard(id, BoardUpdateRemote(name)))
    }

    fun deleteBoard(board: Board) = viewModelScope.launch(Dispatchers.Main) {
        val response = repo.deleteBoard(board.id)
        when (response.status) {
            Status.SUCCESS -> {
                boardsNavigator.navigateToBoards()
            }
            Status.ERROR -> {
                boardsNavigator.errorSnackBar(response.message!!)
            }
            Status.LOADING -> {
            }
        }
    }
}
