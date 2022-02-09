package com.intive.patronage.retro

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel(val app: Application) : ViewModel() {

    @Suppress("DEPRECATION")
    fun hasNoNetwork(): Boolean {
        var connected = false
        try {
            val cm =
                app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected

            return !connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message!!)
        }
        return !connected
    }
}
