package com.ahmedkhalifa.careerlinkapp.screens.onboarding

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.ahmedkhalifa.careerlinkapp.graphs.AuthScreen
import com.ahmedkhalifa.careerlinkapp.graphs.Graph
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppMainColor
import com.ahmedkhalifa.careerlinkapp.ui.theme.Tajawal
import com.ahmedkhalifa.careerlinkapp.viewmodel.SettingsViewModel


@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isFirstTimeLaunch by viewModel.isFirstTimeLaunch.collectAsState(initial = null)
    val updatedLaunchState = rememberUpdatedState(newValue = isFirstTimeLaunch)
    val context = LocalContext.current

    var notificationPermissionGranted by remember { mutableStateOf(false) }
    var storagePermissionGranted by remember { mutableStateOf(false) }
    var mediaImagesPermissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { entry ->
            when (entry.key) {
                Manifest.permission.POST_NOTIFICATIONS -> {
                    notificationPermissionGranted = entry.value
                    if (!entry.value) {
                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    storagePermissionGranted = entry.value
                }
                Manifest.permission.READ_MEDIA_IMAGES -> {
                    mediaImagesPermissionGranted = entry.value
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val permissionsToRequest = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            permissionsToRequest.add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionLauncher.launch(permissionsToRequest.toTypedArray())
    }

    LaunchedEffect(updatedLaunchState.value) {
        Log.d("isFirstTime", "Updated Value: ${updatedLaunchState.value}")
        if (updatedLaunchState.value == true) {
            navController.navigate(Graph.AUTHENTICATION) {
                popUpTo(AuthScreen.OnBoarding.route) {
                    inclusive = true
                }
            }
        }
    }

    OnBoardingScreenContent(
        onGetStartedClick = {
            Log.d("ButtonClick", "Button Pressed")
            viewModel.setFirstTimeLaunch(true)
        }
    )
}

@Composable
fun OnBoardingScreenContent(
    onGetStartedClick: () -> Unit
) {
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

            Button(
                onClick = onGetStartedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppMainColor,
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.get_started),
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        fontFamily = Tajawal
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewOnboardingScreen() {
    OnBoardingScreenContent(onGetStartedClick = {})
}