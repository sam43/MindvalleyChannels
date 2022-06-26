package com.sam43.mindvalleychannels.utils

import android.view.Window
import android.view.WindowManager

object AppConstants {
    var TAG = javaClass.simpleName.toString()
    const val DATABASE_NAME = "mind_valley_app_db"
    const val TYPE_RAIL_PORTRAIT = 0
    const val TYPE_RAIL_LANDSCAPE = 1
    const val TYPE_GRID_CATEGORY = 2

    const val SOCKET_TIME_OUT_EXCEPTION =
        "Request timed out while trying to connect. Please ensure you have a strong signal and try again."
    const val UNKNOWN_NETWORK_EXCEPTION =
        "An unexpected error has occurred. Please check your network connection and try again."
    const val CONNECT_EXCEPTION =
        "Could not connect to the server. Please check your internet connection and try again."
    const val UNKNOWN_HOST_EXCEPTION =
        "Couldn't connect to the server at the moment. Please try again in a few minutes."
    const val SSL_EXCEPTION =
        "Software caused connection abort. Please check your internet connection and try again."

    // extensions
    fun Window.transparentStatusBar() {
        this.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
    }

    inline fun <reified T> List<*>.asListOfType(): List<T>? =
        if (all { it is T })
            @Suppress("UNCHECKED_CAST")
            this as List<T> else
            null

    inline fun <reified T> List<*>.isListOfType(): Boolean =
        if (all { it is T })
            @Suppress("UNCHECKED_CAST")
            true else false

    inline fun <reified T> MutableList<*>.isMutableListOfType(): Boolean =
        if (all { it is T })
            @Suppress("UNCHECKED_CAST")
            true else false
}