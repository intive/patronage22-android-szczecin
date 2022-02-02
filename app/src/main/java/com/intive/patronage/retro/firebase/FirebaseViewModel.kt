package com.intive.patronage.retro.firebase

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

class FirebaseViewModel(private val firebaseSignIn: FirebaseSignIn) : ViewModel() {

    fun isUserLogged() = firebaseSignIn.getUser() != null
    fun signIn(app: AppCompatActivity) {
        firebaseSignIn.signIn(app)
    }
    fun isSignInActivityReady() = firebaseSignIn.isReady()
}
