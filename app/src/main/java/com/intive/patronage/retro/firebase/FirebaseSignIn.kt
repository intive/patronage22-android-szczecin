package com.intive.patronage.retro.firebase

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser

interface FirebaseSignIn {

    fun getUser(): FirebaseUser?
    fun signIn(app: AppCompatActivity)
}
