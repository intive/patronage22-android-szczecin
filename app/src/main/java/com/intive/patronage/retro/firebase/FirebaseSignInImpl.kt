package com.intive.patronage.retro.firebase

import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth

class FirebaseSignInImpl : FirebaseSignIn {

    private val intent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setIsSmartLockEnabled(false)
        .build()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun getUser() = user

    override fun signIn(app: AppCompatActivity) {
        app.registerForActivityResult(FirebaseAuthUIActivityResultContract()) {}.launch(intent)
    }
}
