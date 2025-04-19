package com.ahmedkhalifa.careerlinkapp.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ahmedkhalifa.careerlinkapp.screens.JobSearchScreen
import com.ahmedkhalifa.careerlinkapp.screens.onboarding.SplashScreen

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
        profileNavGraph(navController=navController)
        detailsNavGraph(navController = navController)
        composable(SearchScreen.Search.route) {
            JobSearchScreen(navController = navController)
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

