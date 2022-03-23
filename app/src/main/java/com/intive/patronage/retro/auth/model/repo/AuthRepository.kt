package com.intive.patronage.retro.auth.model.repo

import android.content.Intent
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    fun getUser(): FirebaseUser?
    fun getIntent(): Intent
    fun getActivityResultContract(): FirebaseAuthUIActivityResultContract
    fun logOut()
}
