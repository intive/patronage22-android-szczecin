package com.intive.patronage.retro.main.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.intive.patronage.retro.auth.model.repo.AuthRepository

class MainViewModel(val app: Application, private val authRepository: AuthRepository) : ViewModel() {

    fun isLogged() = authRepository.getUser() != null
    fun isReady() = authRepository.isReady()
    fun getIntent() = authRepository.getIntent()
    fun isBackPressed() = authRepository.isBackPressed()
    fun logOut() {
        authRepository.logOut()
    }
    fun getActivityResultContract() = authRepository.getActivityResultContract()
    fun onResult(result: FirebaseAuthUIAuthenticationResult) {
        authRepository.onResult(result)
    }

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
}
