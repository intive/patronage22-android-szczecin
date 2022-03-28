package com.intive.patronage.retro.retro.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.retro.model.entity.BoardCardsRemote
import com.intive.patronage.retro.retro.model.repo.RetroRepository
import com.intive.patronage.retro.retro.presentation.entity.BoardCards
import com.intive.patronage.retro.retro.presentation.entity.Columns
import com.intive.patronage.retro.retro.presentation.entity.RetroDetails
import kotlinx.coroutines.Dispatchers

class RetroViewModel(private val repo: RetroRepository) : ViewModel() {

    fun retroConfiguration(id: Int): LiveData<Resource<List<Columns>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getRetroConfiguration(id))
    }

    fun retroDetails(id: Int): LiveData<Resource<List<RetroDetails>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getRetroDetails(id))
    }

    fun addCards(id: Int, columnId: Int, text: String): LiveData<Resource<BoardCardsRemote>> = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.postCard(id, BoardCards(0, text, columnId, "", emptyList())))
    }
}
