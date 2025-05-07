package com.example.demophotosearchapp.ui.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

// Animation trượt từ phải và mờ dần khi vào
fun slideInFromRightFadeIn(durationMillis: Int = 400): EnterTransition {
    return slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(durationMillis)) +
            fadeIn(animationSpec = tween(durationMillis))
}

// Animation trượt sang trái và mờ dần khi thoát
fun slideOutToLeftFadeOut(durationMillis: Int = 400): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(durationMillis)) +
            fadeOut(animationSpec = tween(durationMillis))
}

// Animation trượt từ trái và mờ dần khi vào (cho popEnter)
fun slideInFromLeftFadeIn(durationMillis: Int = 400): EnterTransition {
    return slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(durationMillis)) +
            fadeIn(animationSpec = tween(durationMillis))
}

// Animation trượt sang phải và mờ dần khi thoát (cho popExit)
fun slideOutToRightFadeOut(durationMillis: Int = 400): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(durationMillis)) +
            fadeOut(animationSpec = tween(durationMillis))
}

// Animation mờ dần khi vào
fun fadeInAnimation(durationMillis: Int = 500): EnterTransition {
    return fadeIn(animationSpec = tween(durationMillis))
}

// Animation mờ dần khi thoát
fun fadeOutAnimation(durationMillis: Int = 300): ExitTransition {
    return fadeOut(animationSpec = tween(durationMillis))
}

// Animation trượt nhẹ từ trái
fun slideInFromLeftSlightly(durationMillis: Int = 300, offsetFactor: Float = -0.5f): EnterTransition {
    return slideInHorizontally(initialOffsetX = { (it * offsetFactor).toInt() }, animationSpec = tween(durationMillis))
}

// Animation trượt nhẹ sang phải
fun slideOutToRightSlightly(durationMillis: Int = 300, offsetFactor: Float = 0.5f): ExitTransition {
    return slideOutHorizontally(targetOffsetX = { (it * offsetFactor).toInt() }, animationSpec = tween(durationMillis))
}