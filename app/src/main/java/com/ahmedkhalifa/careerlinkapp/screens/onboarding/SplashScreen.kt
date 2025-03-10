package com.ahmedkhalifa.careerlinkapp.screens.onboarding

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.graphs.Graph
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor
import kotlinx.coroutines.delay
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun SplashScreen(
    navController: NavController
) {
    SplashScreenContent(navController)
}

@Composable
private fun SplashScreenContent(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = AppMainColor
    )
    systemUiController.setStatusBarColor(
        color = AppMainColor
    )

    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate(Graph.HOME)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(AppMainColor)

    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.scale(scale.value)
        )
    }
}
