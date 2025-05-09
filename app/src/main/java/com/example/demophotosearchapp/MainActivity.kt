package com.example.demophotosearchapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import com.example.demophotosearchapp.ui.DemoApp
import com.example.demophotosearchapp.ui.GlobalViewModel
import com.example.demophotosearchapp.ui.LocalGlobalViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG: String = "MainActivity"
    private external fun decryptDecrypt(): String
    private val globalViewModel: GlobalViewModel by viewModels()
    private var networkState = mutableStateOf(true)
    private val blurRequest = mutableStateOf(false)
    private var connectivityManager: ConnectivityManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalGlobalViewModel provides globalViewModel) {
                DemoApp( )
            }
        }

        initNetworkListener()
    }

    private fun init() {

    }
    /**
     * Listener net work connection
     */
    private fun initNetworkListener() {
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager?.activeNetwork == null) {
            runOnUiThread {
                globalViewModel.setNetworkState(false)
            }
        }
        connectivityManager?.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    globalViewModel.setNetworkState(true)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                runOnUiThread {
                    globalViewModel.setNetworkState(false)
                }

            }

            override fun onUnavailable() {
                super.onUnavailable()
                runOnUiThread {
                    globalViewModel.setNetworkState(false)
                }
            }
        })
        /* Lấy thông tin WiFi lúc khởi tạo
        getNetworkInfo()
        */
    }
}

