package com.example.demophotosearchapp.ui.component

import android.app.Activity
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity

/**
 * Created by ElChuanmen on 6/5/2025.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */

@Composable
fun isKeyboardVisible(): Boolean {
    val ime = WindowInsets.ime
    return ime.getBottom(LocalDensity.current) > 0
}
@Composable
fun rememberKeyboardVisibility(): State<Boolean> {
    val context = LocalContext.current
    val rootView = (context as Activity).window.decorView
    val keyboardVisible = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardVisible.value = keypadHeight > screenHeight * 0.15
        }

        rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return keyboardVisible
}
