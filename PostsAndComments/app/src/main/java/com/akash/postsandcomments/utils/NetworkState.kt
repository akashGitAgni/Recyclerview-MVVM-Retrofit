package com.akash.postsandcomments.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class NetworkState(private val context: Application) {

    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        val networkInfo = cm.activeNetworkInfo;
        return (networkInfo != null && networkInfo.isConnected)
    }

}