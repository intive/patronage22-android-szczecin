package com.intive.patronage.retro.main.presentation.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.retro.auth.model.repo.AuthRepository
import com.intive.patronage.retro.auth.model.service.AuthToken

class MainViewModel(val app: Application, private val authRepository: AuthRepository, private val authToken: AuthToken) : ViewModel() {

    var isDialogClosed = MutableLiveData<Boolean>()

    fun isLogged() = authRepository.getUser() != null
    fun getIntent() = authRepository.getIntent()
    fun getUser() = authRepository.getUser()
    fun logOut() {
        authRepository.logOut()
    }

    fun getActivityResultContract() = authRepository.getActivityResultContract()
    fun getPicUri(): Uri? = getUser()?.photoUrl
    fun getDisplayName() = getUser()?.displayName
    fun getEmail() = getUser()?.email

    fun isUserTokenLoaded(owner: LifecycleOwner): Boolean {
        var isLoaded = false
        authToken.getToken().observe(owner) {
            if (it.isNotEmpty()) {
                isLoaded = true
            }
        }
        return isLoaded
    }
}
