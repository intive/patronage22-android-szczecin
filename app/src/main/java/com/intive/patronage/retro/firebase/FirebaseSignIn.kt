package com.intive.patronage.retro.firebase

import android.content.Intent
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseSignIn {
    fun getUser(): FirebaseUser?
    fun isReady(): Boolean
    fun getIntent(): Intent
    fun logOut()
    fun onResult(result: FirebaseAuthUIAuthenticationResult)
    fun isBackPressed(): Boolean
    fun getActivityResultContract(): FirebaseAuthUIActivityResultContract
}
