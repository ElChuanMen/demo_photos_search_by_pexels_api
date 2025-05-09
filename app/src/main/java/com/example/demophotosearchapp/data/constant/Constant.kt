package com.example.demophotosearchapp.data.constant

import android.content.res.Resources
import com.example.demophotosearchapp.utils.extension.dpToPx


object Constant {
    const val AnimDuration: Long = 200
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    val density = Resources.getSystem().displayMetrics.density
    val bottomBarHeight = density * 88f
    val pipWidth = (screenWidth * 0.6).toInt()
    val pipHeight = pipWidth * 9 / 16

    /**
     * No internet
     */
    const val NO_INTERNET = "NO_INTERNET"
    /**
     * ConfigApp
     */
    const val DATABASE_NAME = "demo-photo-search"
    const val APP_PREFERENCE_NAME = "demo_searches"



}