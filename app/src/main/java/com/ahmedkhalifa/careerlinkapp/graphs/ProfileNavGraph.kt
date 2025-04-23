package com.ahmedkhalifa.careerlinkapp.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ahmedkhalifa.careerlinkapp.screens.profile.ApplicationsScreen
import com.ahmedkhalifa.careerlinkapp.screens.profile.EditProfileScreen
import com.ahmedkhalifa.careerlinkapp.screens.profile.NotificationSettingsScreen
import com.ahmedkhalifa.careerlinkapp.screens.profile.ProfileScreen
import com.ahmedkhalifa.careerlinkapp.screens.profile.SavedJobsScreen


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PROFILE,
        startDestination = Graph.Routes.PROFILE
    ) {
        composable(Graph.Routes.PROFILE) {
            ProfileScreen(navController)
        }
        composable(ProfileGraph.EditProfile.route) {
            EditProfileScreen()
        }
        composable(ProfileGraph.Applications.route) {
            ApplicationsScreen(navController=navController)
        }
        composable(ProfileGraph.Notifications.route) {
            NotificationSettingsScreen()
        }
        composable(ProfileGraph.SavedJobs.route) {
            SavedJobsScreen(navController)
        }

    }
}

sealed class ProfileGraph(val route: String) {
    data object EditProfile : ProfileGraph(route = "EDIT_PROFILE")
    data object Applications : ProfileGraph(route = "APPLICATIONS")
    data object Notifications : ProfileGraph(route = "NOTIFICATIONS")
    data object SavedJobs : ProfileGraph(route = "SAVED_JOBS")
}