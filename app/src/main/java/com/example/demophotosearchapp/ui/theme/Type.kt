package com.example.demophotosearchapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.demophotosearchapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
val beVietnamProFamily = FontFamily(
    Font(R.font.be_vietnam_pro_regular, FontWeight.Normal),
    Font(R.font.be_vietnam_pro_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.be_vietnam_pro_medium, FontWeight.Medium),
    Font(R.font.be_vietnam_pro_semi_bold, FontWeight.SemiBold),
    Font(R.font.be_vietnam_pro_bold, FontWeight.Bold),
    Font(R.font.be_vietnam_pro_extra_bold, FontWeight.ExtraBold)
)
val playWriteFamily = FontFamily(
    Font(R.font.playwrite_regular, FontWeight.Medium)
)

@Composable
fun customTextStyle(
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp,
    fontStyle: FontStyle = FontStyle.Normal,
    textAlign: TextAlign = TextAlign.Start,
    shadow: Shadow=Shadow.None
): TextStyle {
    return remember(fontFamily, fontWeight, fontSize) {
        TextStyle(
            fontSize = fontSize,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            textAlign = textAlign,
            shadow = shadow
        )
    }
}


@Composable
fun playWriteMediumRegular(fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp, shadow: Shadow=Shadow.None) =
    customTextStyle(playWriteFamily, FontWeight.Bold, fontSize, shadow = shadow)

@Composable
fun beVietNamRegular(
    fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp,
    textAlign: TextAlign = TextAlign.Start
) = customTextStyle(beVietnamProFamily, FontWeight.Normal, fontSize, textAlign = textAlign)

@Composable
fun beVietNamBold(fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp) =
    customTextStyle(beVietnamProFamily, FontWeight.Bold, fontSize)

@Composable
fun beVietNamMedium(
    fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp,
    textAlign: TextAlign = TextAlign.Start,  shadow: Shadow=Shadow.None
) = customTextStyle(beVietnamProFamily, FontWeight.Medium, fontSize, textAlign = textAlign,shadow=shadow)

@Composable
fun beVietNamSemibold(fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp) =
    customTextStyle(beVietnamProFamily, FontWeight.SemiBold, fontSize)

@Composable
fun beVietNamItalic(fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp) =
    customTextStyle(beVietnamProFamily, FontWeight.Normal, fontSize, fontStyle = FontStyle.Italic)

@Composable
fun beVietNamExtraBold(fontSize: TextUnit = dimensionResource(R.dimen.text_size_18).value.sp) =
    customTextStyle(beVietnamProFamily, FontWeight.ExtraBold, fontSize)