package com.intive.patronage.retro.main.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.intive.patronage.retro.auth.model.repo.AuthRepository
import com.intive.patronage.retro.auth.model.service.AuthToken

class MainViewModel(val app: Application, private val authRepository: AuthRepository, private val authToken: AuthToken) : ViewModel() {

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

    @Suppress("DEPRECATION")
    fun hasNoNetwork(): Boolean {
        var connected = false
        try {
            val cm =
                app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected

            return !connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message!!)
        }
        return !connected
    }

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
