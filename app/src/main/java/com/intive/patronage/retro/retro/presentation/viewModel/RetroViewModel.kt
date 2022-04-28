package com.intive.patronage.retro.retro.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.intive.patronage.retro.common.api.Resource
import com.intive.patronage.retro.common.api.Status
import com.intive.patronage.retro.retro.model.repo.RetroRepository
import com.intive.patronage.retro.retro.presentation.entity.BoardCards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RetroViewModel(private val repo: RetroRepository) : ViewModel() {

    private var stopHeartBeat = true

    fun retroConfiguration(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getRetroConfiguration(id))
    }

    fun retroConfigurationRefresh(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getRetroConfiguration(id))
    }

    fun retroDetailsRefresh(id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.getRetroDetails(id))
    }

    fun addCards(id: Int, columnId: Int, text: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(repo.postCard(id, BoardCards(0, text, columnId, "", emptyList())))
    }

    fun startHeartBeatRetro(id: Int) = liveData(Dispatchers.IO) {
        while (stopHeartBeat) {
            emit(Resource.loading(null))
            emit(repo.getRetroDetails(id))
            delay(5000)
        }
    }

    fun stopHeartBeat() {
        stopHeartBeat = false
    }

    fun vote(id: Int) = viewModelScope.launch(Dispatchers.Main) {
        val response = repo.postVote(id)
        when (response.status) {
            Status.SUCCESS -> {
                Log.i("SUCCESS ", "Vote for Card id $id was given")
            }
            Status.ERROR -> {
                Log.i("ERROR ", "${response.message}")
            }
            else -> {}
        }
    }
}
