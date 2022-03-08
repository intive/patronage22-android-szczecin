package com.intive.patronage.retro.board.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.presentation.entity.Board
import com.intive.patronage.retro.common.api.Resource
import kotlinx.coroutines.Dispatchers

class BoardViewModel(private val repo: BoardRepository) : ViewModel() {

    val boards: LiveData<Resource<List<Board>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getBoards())
    }
}
