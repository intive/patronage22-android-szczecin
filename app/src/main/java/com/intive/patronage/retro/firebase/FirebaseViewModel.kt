package com.intive.patronage.retro.firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

class FirebaseViewModel(private val firebaseSignIn: FirebaseSignIn) : ViewModel() {
    fun isLogged() = firebaseSignIn.getUser() != null
    fun isReady() = firebaseSignIn.isReady()
    fun getIntent() = firebaseSignIn.getIntent()
    fun getResultLauncher(app: AppCompatActivity) = firebaseSignIn.getResultLauncher(app)
    fun isBackPressed() = firebaseSignIn.isBackPressed()
    fun logOut(context: Context) {
        firebaseSignIn.logOut(context)
    }
}
