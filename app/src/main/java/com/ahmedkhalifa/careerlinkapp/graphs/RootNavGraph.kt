package com.ahmedkhalifa.careerlinkapp.graphs

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ahmedkhalifa.careerlinkapp.screens.JobSearchScreen
import com.ahmedkhalifa.careerlinkapp.screens.onboarding.SplashScreen
import com.ahmedkhalifa.careerlinkapp.screens.details.ApplicationStatusScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        composable(route = Graph.SPLASH) {
            SplashScreen(navController = navController)
        }
        authNavGraph(navController = navController)
        homeNavGraph(navController = navController)
        profileNavGraph(navController = navController)
        detailsNavGraph(navController = navController)
        composable(SearchScreen.Search.route) {
            JobSearchScreen(navController = navController)
        }

        composable(
            route = "application_status?jobId={jobId}&jobTitle={jobTitle}",
            arguments = listOf(
                navArgument("jobId") { type = NavType.StringType; nullable = true },
                navArgument("jobTitle") { type = NavType.StringType; nullable = true }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "careerlink://application_status?jobId={jobId}&jobTitle={jobTitle}"
                }
            )
        ) { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            val jobTitle = backStackEntry.arguments?.getString("jobTitle")
            Log.d("RootNavigationGraph", "Navigating to ApplicationStatusScreen with jobId=$jobId, jobTitle=$jobTitle")
            ApplicationStatusScreen(
                navController = navController,
                jobId = jobId,
                jobTitle = jobTitle
            )
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
    const val SPLASH = "splash"
    const val PROFILE = "profile_graph"

    object Routes {
        const val HOME = "home"
        const val SETTINGS = "settings"
        const val PROFILE = "profile"
    }
}
