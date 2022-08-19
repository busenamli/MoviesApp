package com.busenamli.moviesapp.util

import android.content.Context
import android.net.ConnectivityManager

object Network {
    private val isConnected: Boolean = true

    fun checkConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        return if (activeNetwork != null) {
            isConnected
        } else {
            !isConnected
        }
    }
}