package com.intive.patronage.retro.firebase

import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

class FirebaseViewModel(private val firebaseSignIn: FirebaseSignIn) : ViewModel() {
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
}
