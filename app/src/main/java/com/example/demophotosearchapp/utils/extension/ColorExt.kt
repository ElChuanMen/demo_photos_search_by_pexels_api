package com.example.demophotosearchapp.utils.extension


import androidx.compose.ui.graphics.Color

/**
 * Created by ElChuanmen on 7/16/2024.
 * Telegram : elchuanmen
 * Phone :0949514503-0773209008
 * Mail :doanvanquang146@gmail.com
 */
val String.color
    get() = Color(android.graphics.Color.parseColor(this))

fun Color.Companion.getRandomColors(quantity: Int): List<Color> {
    val colors = mutableListOf<Color>()
    repeat(quantity) {
        colors.add(
            Color(
                red = (0..255).random() / 255f,
                green = (0..255).random() / 255f,
                blue = (0..255).random() / 255f
            )
        )
    }
    return colors
}