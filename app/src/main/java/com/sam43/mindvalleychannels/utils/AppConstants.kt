package com.sam43.mindvalleychannels.utils

import android.view.Window
import android.view.WindowManager

object AppConstants {
    var TAG = javaClass.simpleName.toString()
    const val DATABASE_NAME = "mind_valley_app_db"
    const val TYPE_RAIL_PORTRAIT = 0
    const val TYPE_RAIL_LANDSCAPE = 1
    const val TYPE_GRID_CATEGORY = 2
    const val TYPE_SHIMMER_LAYOUT_PORTRAIT = 3
    //const val TYPE_SHIMMER_LAYOUT_LANDSCAPE = 4

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