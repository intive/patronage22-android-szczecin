package com.intive.patronage.retro

import androidx.lifecycle.ViewModel
import com.intive.patronage.retro.tets_koin.HelloRepository

class MainViewModel(val repo: HelloRepository) : ViewModel() {
    fun sayHello() = "${repo.giveHello()} from $this"
}
