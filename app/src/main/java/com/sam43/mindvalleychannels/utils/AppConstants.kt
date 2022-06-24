package com.sam43.mindvalleychannels.utils

import android.util.Log
import android.view.Window
import android.view.WindowManager
import org.jetbrains.annotations.TestOnly
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.net.URL
import java.nio.charset.Charset

object AppConstants {
    var TAG = javaClass.simpleName.toString()

    const val LINEAR_LAYOUT: String = "LINEAR_LAYOUT"
    const val DATABASE_NAME = "mind_valley_app_db"
    const val TYPE_RAIL_PORTRAIT = 0
    const val TYPE_RAIL_LANDSCAPE = 1
    const val TYPE_GRID_CATEGORY = 2

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

    inline fun <reified T> MutableList<*>.isListOfType(): Boolean =
        if (all { it is T })
            @Suppress("UNCHECKED_CAST")
            true else false
}