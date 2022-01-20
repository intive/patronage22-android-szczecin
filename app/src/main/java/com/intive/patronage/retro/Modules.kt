package com.intive.patronage.retro

import com.intive.patronage.retro.tets_koin.HelloRepository
import com.intive.patronage.retro.tets_koin.HelloRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<HelloRepository> { HelloRepositoryImpl() }

    viewModel { MainViewModel(get()) }
}
