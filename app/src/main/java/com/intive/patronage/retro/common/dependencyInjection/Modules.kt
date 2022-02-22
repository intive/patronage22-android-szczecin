package com.intive.patronage.retro.common.dependencyInjection

import android.content.Context
import android.net.ConnectivityManager
import com.intive.patronage.retro.auth.model.repo.AuthRepository
import com.intive.patronage.retro.auth.model.service.Auth
import com.intive.patronage.retro.board.model.fakeApi.FakeBoardsApi
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.model.repo.FakeBoardsRepository
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.common.network.CheckNetworkConnect
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<AuthRepository> { Auth(androidContext()) }
    factory { CheckNetworkConnect(androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }

    factory<BoardRepository> { FakeBoardsRepository(FakeBoardsApi()) }

    viewModel { MainViewModel(get(), get()) }
    viewModel { BoardViewModel(get()) }
}
