package com.intive.patronage.retro.common.dependencyInjection

import android.content.Context
import android.net.ConnectivityManager
import com.intive.patronage.retro.auth.model.repo.AuthRepository
import com.intive.patronage.retro.auth.model.service.Auth
import com.intive.patronage.retro.auth.model.service.Token
import com.intive.patronage.retro.board.model.repo.BoardApiImpl
import com.intive.patronage.retro.board.model.repo.BoardRepository
import com.intive.patronage.retro.board.presentation.view.BoardsFragment
import com.intive.patronage.retro.board.presentation.viewModel.BoardViewModel
import com.intive.patronage.retro.common.api.AuthInterceptor
import com.intive.patronage.retro.common.api.HttpClient
import com.intive.patronage.retro.common.api.ResponseHandler
import com.intive.patronage.retro.common.api.RestClient
import com.intive.patronage.retro.common.network.CheckNetworkConnect
import com.intive.patronage.retro.main.presentation.viewModel.MainViewModel
import com.intive.patronage.retro.retro.model.repo.RetroApiImpl
import com.intive.patronage.retro.retro.model.repo.RetroRepository
import com.intive.patronage.retro.retro.presentation.view.RetroFragment
import com.intive.patronage.retro.retro.presentation.viewModel.RetroViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<AuthRepository> { Auth(androidContext(), get()) }
    factory { CheckNetworkConnect(androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }
    factory { ResponseHandler() }
    factory { BoardRepository(get(), get()) }
    factory { BoardsFragment() }
    factory { RetroFragment() }
    factory { RetroRepository(get(), get()) }

    viewModel { MainViewModel(get(), get()) }
    viewModel { BoardViewModel(get()) }
    viewModel { RetroViewModel(get()) }
}

val networkModule = module {
    single { Token() }
    single { AuthInterceptor(get()) }
    factory { HttpClient(get()) }
    single { RestClient(get(), getProperty("base_url")) }
    factory { BoardApiImpl(get()) }
    factory { RetroApiImpl(get()) }
}
