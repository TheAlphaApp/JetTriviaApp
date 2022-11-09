package com.appdexon.jettriviaapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.appdexon.jettriviaapp.util.AppColors
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//private val DarkColorPalette = darkColors(
//    primary = Purple200,
//    primaryVariant = Purple700,
//    secondary = Teal200
//)



private val LightColorPalette = lightColors(
    primary = AppColors.mTeal,
    primaryVariant = AppColors.mDarkTeal,
    secondary = AppColors.mYellow,
    secondaryVariant = AppColors.mYellow

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun JetTriviaAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(
            color = AppColors.mTeal
    )


    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}