package com.intive.patronage.retro.auth.model.service

import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class Token {
    private var token = String()
    private val refreshRate = 600L
    private lateinit var scheduledExecutor: ScheduledExecutorService
    private var isRefreshing = false

    private fun generateToken() {
        FirebaseAuth.getInstance().currentUser?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result?.token.toString()
                }
            }
    }

    private fun refreshToken(delay: Long) {
        scheduledExecutor.schedule({
            generateToken()
            refreshToken(refreshRate)
        }, delay, TimeUnit.SECONDS)
    }

    fun getToken() = token

    fun stopRefreshToken() {
        isRefreshing = false
        scheduledExecutor.shutdown()
        token = String()
    }

    fun startRefreshToken() {
        if (!isRefreshing) {
            isRefreshing = true
            scheduledExecutor = Executors.newSingleThreadScheduledExecutor()
            refreshToken(0)
        }
    }
}
