package com.intive.patronage.retro.auth.model.service

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.intive.patronage.retro.auth.model.repo.AuthRepository

class Auth(private val context: Context, private val userToken: Token) : AuthRepository {

    private val intent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setIsSmartLockEnabled(false)
        .build()
    private val user = FirebaseAuth.getInstance().currentUser
    private var isBackPressed = false

    override fun getUser() = user
    override fun getIntent(): Intent = intent
    override fun getActivityResultContract() = FirebaseAuthUIActivityResultContract()

    override fun logOut() {
        userToken.stopRefreshToken()
        AuthUI.getInstance().signOut(context)
    }

    override fun onResult(result: FirebaseAuthUIAuthenticationResult) {
        isBackPressed = false

        if (result.idpResponse == null) {
            isBackPressed = true
        } else {
            userToken.startRefreshToken()
        }
    }

    override fun isBackPressed() = isBackPressed
}
