package com.intive.patronage.retro.common.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData

class NetworkConnectionObserver(private val connectivityManager: ConnectivityManager) {

    val status = MutableLiveData(hasNoNetwork())

    fun start() {
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            status.postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            status.postValue(false)
        }
    }

    @Suppress("DEPRECATION")
    private fun hasNoNetwork(): Boolean {
        val nInfo = connectivityManager.activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }
}
