package com.intive.patronage.retro.firebase

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class FirebaseSignInImpl : FirebaseSignIn {
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

    override fun getResultLauncher(app: AppCompatActivity) =
        app.registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            res ->
            onResult(res, app)
        }

    override fun logOut(context: Context) {
        AuthUI.getInstance().signOut(context)
    }

    override fun onResult(result: FirebaseAuthUIAuthenticationResult, context: Context) {
        isReady = true
        isBackPressed = false

        if (result.idpResponse == null) {
            isBackPressed = true
            Toast.makeText(context, "Sign-in flow cancelled!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun isBackPressed() = isBackPressed
}
