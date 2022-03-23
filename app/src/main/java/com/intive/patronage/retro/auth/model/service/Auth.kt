package com.intive.patronage.retro.auth.model.service

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.intive.patronage.retro.auth.model.repo.AuthRepository

class Auth(private val context: Context, private val userToken: Token) : AuthRepository {

    override fun getUser() = FirebaseAuth.getInstance().currentUser
    override fun getIntent() = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setIsSmartLockEnabled(false)
        .build()
    override fun getActivityResultContract() = FirebaseAuthUIActivityResultContract()
    override fun logOut() {
        userToken.stopRefreshToken()
        AuthUI.getInstance().signOut(context)
    }
}
