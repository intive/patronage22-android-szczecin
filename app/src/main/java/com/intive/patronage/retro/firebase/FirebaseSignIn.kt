package com.intive.patronage.retro.firebase

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseSignIn {
    fun getUser(): FirebaseUser?
    fun isReady(): Boolean
    fun getIntent(): Intent
    fun getResultLauncher(app: AppCompatActivity): ActivityResultLauncher<Intent>
    fun logOut(context: Context)
    fun onResult(result: FirebaseAuthUIAuthenticationResult, context: Context)
    fun isBackPressed(): Boolean
}
