package com.intive.patronage.retro

import com.intive.patronage.retro.firebase.FirebaseSignIn
import com.intive.patronage.retro.firebase.FirebaseSignInImpl
import com.intive.patronage.retro.firebase.FirebaseViewModel
import com.intive.patronage.retro.tets_koin.HelloRepository
import com.intive.patronage.retro.tets_koin.HelloRepositoryImpl
import com.intive.patronage.retro.util.CheckNetworkConnect
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {

    single<HelloRepository> { HelloRepositoryImpl() }
    factory<FirebaseSignIn> { FirebaseSignInImpl(androidContext()) }
    factory { CheckNetworkConnect(androidApplication()) }

    viewModel { MainViewModel(get()) }
    viewModel { FirebaseViewModel(get()) }
}
