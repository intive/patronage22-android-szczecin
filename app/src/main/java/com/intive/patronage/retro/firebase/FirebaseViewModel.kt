package com.intive.patronage.retro.firebase

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

class FirebaseViewModel(private val firebaseSignIn: FirebaseSignIn) : ViewModel() {

    fun isUserLogged() = firebaseSignIn.getUser() != null
    fun isSignInActivityReady() = firebaseSignIn.isReady()
    fun getSignInIntent() = firebaseSignIn.getIntent()
    fun getSignInResultLauncher(app: AppCompatActivity) = firebaseSignIn.getResultLauncher(app)
}
