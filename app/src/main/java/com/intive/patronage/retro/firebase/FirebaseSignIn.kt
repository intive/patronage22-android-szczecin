package com.intive.patronage.retro.firebase

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser

interface FirebaseSignIn {

    fun getUser(): FirebaseUser?
    fun isReady(): Boolean
    fun getIntent(): Intent
    fun getResultLauncher(app: AppCompatActivity): ActivityResultLauncher<Intent>
}
