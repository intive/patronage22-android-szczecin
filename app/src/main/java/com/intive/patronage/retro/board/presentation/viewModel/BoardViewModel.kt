package com.intive.patronage.retro.board.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.intive.patronage.retro.R
import com.intive.patronage.retro.board.model.entity.BoardUpdateRemote
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.board.presentation.view.BoardsNavigator
import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class BoardViewModel(private val repo: BoardRepository, private val boardsNavigator: BoardsNavigator) : ViewModel() {

    fun getBoards() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getBoards())
    }

    fun addBoard(boardName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.addBoard(Board(0, "", boardName, 3)))
    }

    fun addUsers(id: Int, users: List<String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.addUsers(id, users))
    }

    fun editBoard(id: Int, name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.updateBoard(id, BoardUpdateRemote(name)))
    }

    fun deleteBoard(board: Board, position: Int) {
        val fragmentContext = boardsNavigator.getContextFromBoardFragment()!!
        val alertDialog = MaterialAlertDialogBuilder(fragmentContext)
        alertDialog.setTitle(R.string.alert_dialog_title)
        alertDialog.setMessage("Do you really want to remove the ${board.name} board")
        alertDialog.setCancelable(false)
        alertDialog
            .setPositiveButton(R.string.alert_dialog_positive_button) { dialog, _ ->
                dialog.cancel()
                delete(board, position)
            }
        alertDialog
            .setNegativeButton(R.string.alert_dialog_negative_button) { dialog, _ ->
                dialog.cancel()
            }
        alertDialog.show()
    }

    suspend fun getUsers(email: String): Flow<List<String>> {
        val emails = repo.getUsers(email)
        return when (emails.status) {
            Status.SUCCESS -> {
                flow { emit(emails.data!!) }
            }
            Status.ERROR -> {
                flow { emit(listOf("")) }
            }
            else -> {
                flow { emit(emptyList()) }
            }
        }
    }

    private fun delete(board: Board, position: Int) = viewModelScope.launch(Dispatchers.Main) {
        boardsNavigator.recyclerAdapterProgress(true, position)
        val response = repo.deleteBoard(board.id)
        when (response.status) {
            Status.SUCCESS -> {
                boardsNavigator.recyclerAdapterProgress(false, position)
                boardsNavigator.navigateToBoards()
            }
            Status.ERROR -> {
                boardsNavigator.recyclerAdapterProgress(false, position)
                boardsNavigator.errorSnackBar(response.message!!)
            }
            else -> {}
        }
    }
}
