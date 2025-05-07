package com.example.demophotosearchapp.utils.extension

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.demophotosearchapp.R

@Composable
fun dimenSp(@DimenRes resId: Int): TextUnit {
    return dimensionResource(id = resId).value.sp
}
@Composable
fun dimenDp(@DimenRes resId: Int): Dp = dimensionResource(id = resId)
object Dimens {

    // Common spacing
    val Padding6: Dp @Composable get() = dimenDp(R.dimen.size_6)
    val Padding10: Dp @Composable get() = dimenDp(R.dimen.size_10)
    val Padding16: Dp @Composable get() = dimenDp(R.dimen.size_16)
    val Padding18: Dp @Composable get() = dimenDp(R.dimen.size_18)
    val Padding20: Dp @Composable get() = dimenDp(R.dimen.size_20)
    val Padding22: Dp @Composable get() = dimenDp(R.dimen.size_22)
    val Padding24: Dp @Composable get() = dimenDp(R.dimen.size_24)

    // Font sizes
    val Font14: TextUnit @Composable get() = dimenSp(R.dimen.text_size_14)
    val Font16: TextUnit @Composable get() = dimenSp(R.dimen.text_size_16)
    val Font18: TextUnit @Composable get() = dimenSp(R.dimen.text_size_18)
    val Font20: TextUnit @Composable get() = dimenSp(R.dimen.text_size_20)
}