package com.intive.patronage.retro.auth.model.service

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class AuthToken {
    private var authToken = MutableLiveData<String>()
    private val refreshRate = 600L
    private lateinit var scheduledExecutor: ScheduledExecutorService
    private var isRefreshing = false

    fun getToken() = authToken

    fun startRefresh() {
        if (!isRefreshing) {
            isRefreshing = true
            scheduledExecutor = Executors.newSingleThreadScheduledExecutor()
            refresh(0)
        }
    }

    fun stopRefresh() {
        if (isRefreshing) {
            scheduledExecutor.shutdown()
        }
        isRefreshing = false
        authToken.value = String()
    }

    private fun generate() {
        FirebaseAuth.getInstance().currentUser?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authToken.value = task.result?.token.toString()
                }
            }
    }

    private fun refresh(delay: Long) {
        scheduledExecutor.schedule({
            generate()
            refresh(refreshRate)
        }, delay, TimeUnit.SECONDS)
    }
}
