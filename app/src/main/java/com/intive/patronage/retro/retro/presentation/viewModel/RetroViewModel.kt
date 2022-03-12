package com.intive.patronage.retro.retro.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.intive.patronage.retro.retro.model.repo.RetroRepository

class RetroViewModel(private val repo: RetroRepository) : ViewModel() {

    fun takeRetroConfiguration() = repo.getRetroConfiguration()
}
