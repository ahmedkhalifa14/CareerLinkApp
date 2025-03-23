package com.ahmedkhalifa.careerlinkapp.screens.onboarding

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.CustomBtn
import com.ahmedkhalifa.careerlinkapp.graphs.AuthScreen
import com.ahmedkhalifa.careerlinkapp.graphs.Graph
import com.ahmedkhalifa.careerlinkapp.viewmodel.SettingsViewModel


@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isFirstTimeLaunch by viewModel.isFirstTimeLaunch.collectAsState()
    val updatedLaunchState = rememberUpdatedState(newValue = isFirstTimeLaunch)

    LaunchedEffect(updatedLaunchState.value) {
        Log.d("isFirstTime", "Updated Value: ${updatedLaunchState.value}")
        if (updatedLaunchState.value) {
            navController.navigate(Graph.AUTHENTICATION) {
                popUpTo(AuthScreen.OnBoarding.route) {
                    inclusive = true
                }
            }
        }
    }

    OnBoardingScreenContent {
        Log.d("ButtonClick", "Button Pressed")
        viewModel.setFirstTimeLaunch(true)
    }
}

@Composable
fun OnBoardingScreenContent(onGetStartedClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            painter = painterResource(id = R.drawable.onboarding),
            contentDescription = stringResource(R.string.onboarding_image_desc),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.onboarding_title),
                fontSize = 28.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.onboarding_subtitle),
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                lineHeight = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))

            CustomBtn(
                text = stringResource(R.string.get_started), Icons.Default.ArrowForward
            ) {
                onGetStartedClick()
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewSplashScreen() {
    OnBoardingScreenContent(onGetStartedClick = {})
}
