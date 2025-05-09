package com.example.demophotosearchapp.ui

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demophotosearchapp.ui.animation.fadeInAnimation
import com.example.demophotosearchapp.ui.screens.home.HomeScreen
import com.example.demophotosearchapp.ui.screens.SplashScreen
import com.example.demophotosearchapp.ui.screens.photodetails.PhotoDetailsScreens
import com.example.demophotosearchapp.ui.theme.DemoPhotoSearchAppTheme
/**
 * Created by ElChuanmen on 5/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DemoApp() {
    val globalViewModel = LocalGlobalViewModel.current
    val context = LocalContext.current
    val blurRequest by globalViewModel.globalBlur.collectAsStateWithLifecycle()
    val navController = rememberEventAppNavController()
    DemoPhotoSearchAppTheme {
        NavHost(
            navController = navController.navController,
            startDestination = Destinations.Splash.route,
            modifier = Modifier
                .fillMaxSize()
                .blur(if (blurRequest) 30.dp else 0.dp)
        ) {
            composable(Destinations.Splash.route) {
                SplashScreen(
                    navController = navController,
                    onBackStack = { navController.upPress() }
                )
            }
            composable(Destinations.Home.route, enterTransition = { fadeInAnimation(durationMillis = 700)}) {
                HomeScreen(
                    onNavigateTo = { navController.navigateTo(it) },
                    onBackStack = { navController.upPress() }, onExit = { (context as? Activity)?.finish() }
                )
            }

        }
    }
}
