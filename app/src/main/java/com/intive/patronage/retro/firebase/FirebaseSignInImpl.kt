package com.intive.patronage.retro.firebase

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseSignInImpl(_context: Context) : FirebaseSignIn {
    private val context = _context
    private val intent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setIsSmartLockEnabled(false)
        .build()
    private val user = FirebaseAuth.getInstance().currentUser
    private var isReady = false
    private var isBackPressed = false

    override fun getUser() = user
    override fun isReady() = isReady
    override fun getIntent(): Intent = intent

    override fun getActivityResultContract() = FirebaseAuthUIActivityResultContract()

    override fun logOut() {
        AuthUI.getInstance().signOut(context)
    }

    override fun onResult(result: FirebaseAuthUIAuthenticationResult) {
        isReady = true
        isBackPressed = false

        if (result.idpResponse == null) {
            isBackPressed = true
        }
    }

    override fun isBackPressed() = isBackPressed
}
