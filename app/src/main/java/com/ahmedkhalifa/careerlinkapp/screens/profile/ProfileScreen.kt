package com.ahmedkhalifa.careerlinkapp.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedkhalifa.careerlinkapp.R
import com.ahmedkhalifa.careerlinkapp.composable.CircularImage
import com.ahmedkhalifa.careerlinkapp.composable.Header
import com.ahmedkhalifa.careerlinkapp.composable.ProfileCard
import com.ahmedkhalifa.careerlinkapp.composable.SpacerVertical24
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.graphs.ProfileGraph
import com.ahmedkhalifa.careerlinkapp.models.User
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors
import com.ahmedkhalifa.careerlinkapp.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.userState.collectAsState()

    ProfileScreenContent(
        state.value.user,
        onClickEditProfile = {
            navController.navigate(ProfileGraph.EditProfile.route)
        },
        onClickApplications = {
            navController.navigate(ProfileGraph.Applications.route)
        },
        onClickNotificationSettings = {
            navController.navigate(ProfileGraph.Notifications.route)
        },
        onClickSavedJobs = {
            navController.navigate(ProfileGraph.SavedJobs.route)
        },
        onClickLogOut = {

        }

    )
}

@Composable
fun ProfileScreenContent(
    state: User,
    onClickEditProfile: () -> Unit = {},
    onClickApplications: () -> Unit = {},
    onClickNotificationSettings: () -> Unit = {},
    onClickSavedJobs: () -> Unit = {},
    onClickLogOut: () -> Unit = {}
) {
    val screenBackgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColor)
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        CircularImage(imageUrl = state.profilePictureLink)

        SpacerVertical24()
        Header(
            title = state.firstName + " " + state.lastName,
            subTitle = state.email
        )
        SpacerVertical24()
        ProfileCard(
            painter = painterResource(R.drawable.profile_filled_icon),
            cardTitle = stringResource(R.string.edit_profile),
            onClick = {
                onClickEditProfile()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            painter = painterResource(R.drawable.application_ic),
            cardTitle = stringResource(R.string.applications),
            onClick = {
                onClickApplications()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        ProfileCard(
            painter = painterResource(R.drawable.setting_ic),
            cardTitle = stringResource(R.string.notification_settings),
            onClick = {
                onClickNotificationSettings()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            painter = painterResource(R.drawable.favourite_save_icon),
            cardTitle = stringResource(R.string.saved_jobs),
            onClick = {
                onClickSavedJobs()
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        ProfileCard(
            painter = painterResource(R.drawable.logout_icon),
            cardTitle = stringResource(R.string.log_out),
            onClick = {
                onClickLogOut()
            }
        )
        Spacer(modifier = Modifier.height(56.dp))


    }
}
