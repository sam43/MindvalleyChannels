package com.sam43.mindvalleychannels.utils

import android.view.Window
import android.view.WindowManager

object AppConstants {
    const val DATABASE_NAME = "mind_valley_app_db"

    // extensions
    fun Window.transparentStatusBar() {
        this.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,

        );
    }
}