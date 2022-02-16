package com.intive.patronage.retro

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.intive.patronage.retro.firebase.FirebaseSignIn

class MainViewModel(val app: Application, private val firebaseSignIn: FirebaseSignIn) : ViewModel() {

    fun isLogged() = firebaseSignIn.getUser() != null
    fun isReady() = firebaseSignIn.isReady()
    fun getIntent() = firebaseSignIn.getIntent()
    fun isBackPressed() = firebaseSignIn.isBackPressed()
    fun logOut() {
        firebaseSignIn.logOut()
    }
    fun getActivityResultContract() = firebaseSignIn.getActivityResultContract()
    fun onResult(result: FirebaseAuthUIAuthenticationResult) {
        firebaseSignIn.onResult(result)
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
