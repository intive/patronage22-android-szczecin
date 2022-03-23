package com.intive.patronage.retro.main.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.intive.patronage.retro.auth.model.repo.AuthRepository
import com.intive.patronage.retro.auth.model.service.Token

class MainViewModel(val app: Application, private val authRepository: AuthRepository, private val userToken: Token) : ViewModel() {

    fun isLogged() = authRepository.getUser() != null
    fun getIntent() = authRepository.getIntent()
    fun getUser() = authRepository.getUser()
    fun isBackPressed() = authRepository.isBackPressed()
    fun logOut() {
        authRepository.logOut()
    }
    fun getActivityResultContract() = authRepository.getActivityResultContract()
    fun onResult(result: FirebaseAuthUIAuthenticationResult) {
        authRepository.onResult(result)
    }
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
        userToken.observe(owner) {
            if (it) {
                isLoaded = true
            }
        }
        return isLoaded
    }
}
