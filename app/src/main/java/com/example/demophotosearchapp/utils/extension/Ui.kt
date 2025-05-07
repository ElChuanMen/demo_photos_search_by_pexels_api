package com.example.demophotosearchapp.utils.extension

import android.content.res.Resources
import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

fun String.pxToDp(): Int {
    return (this.toInt() / Resources.getSystem().displayMetrics.density).roundToInt()
}

fun String.dpToPx(): Int {
    return (this.toInt() * Resources.getSystem().displayMetrics.density).roundToInt()
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).roundToInt()
}

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).roundToInt()
}
