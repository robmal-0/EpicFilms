package com.example.epicfilms

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.telecom.ConnectionService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

enum class ConnectionState {
    Available,
    Unavailable
}

val Context.connectionService: ConnectivityManager
    get() {
        return getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

var registeredCallback: NetworkCallback? = null

fun Context.registerCallback (callback: (ConnectionState) -> Unit) {
    if (registeredCallback !== null) connectionService.unregisterNetworkCallback(registeredCallback!!)
    registeredCallback = NetworkCallback(callback)
    connectionService.registerDefaultNetworkCallback(registeredCallback!!)
}

fun Context.unregisterCallback () {
    if (registeredCallback !== null) connectionService.unregisterNetworkCallback(registeredCallback!!)
}

fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}