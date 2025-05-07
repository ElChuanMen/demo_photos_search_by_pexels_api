package com.example.demophotosearchapp.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDexApplication
import com.example.demophotosearchapp.BuildConfig
import com.example.demophotosearchapp.data.local.AppPreferences
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@HiltAndroidApp
class MyApplication : MultiDexApplication(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        AppPreferences.init(this)
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
//        System.loadLibrary("native-lib")
    }

}