package com.intive.patronage.retro.board.presentation.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun deleteBoard(board: Board, v: View) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.deleteBoard(board.id)
        when (response.status) {
            Status.SUCCESS -> {
                Log.d("RECYCLER", "I deleted board correct")
            }
            Status.ERROR -> {
                Snackbar.make(v, response.message!!, Snackbar.LENGTH_SHORT).show()
            }
            else -> { Log.d("RECYCLER", "Something else") }
        }
    }
}
