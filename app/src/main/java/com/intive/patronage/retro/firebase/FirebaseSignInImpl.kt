package com.intive.patronage.retro.firebase

import android.content.Intent
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
    private var isReady = false

    override fun getUser() = user
    override fun isReady() = isReady
    override fun getIntent(): Intent = intent
    override fun getResultLauncher(app: AppCompatActivity) =
        app.registerForActivityResult(FirebaseAuthUIActivityResultContract()) { isReady = true }
}
