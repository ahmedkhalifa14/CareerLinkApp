package com.ahmedkhalifa.careerlinkapp.graphs

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ahmedkhalifa.careerlinkapp.BottomBarScreen
import com.ahmedkhalifa.careerlinkapp.ScreenContent
import com.ahmedkhalifa.careerlinkapp.models.Job
import com.ahmedkhalifa.careerlinkapp.screens.details.DetailsScreen
import com.ahmedkhalifa.careerlinkapp.screens.home.HomePage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen("information")
}

object Information : DetailsScreen("information/{jobJson}") {
    fun createRoute(job: Job): String {
        val encodedJob = Uri.encode(Json.encodeToString(job))
        return "information/$encodedJob"
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomePage(navigationController  = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(
                name = BottomBarScreen.Profile.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            ScreenContent(
                name = BottomBarScreen.Settings.route,
                onClick = { }
            )
        }
        detailsNavGraph(navController = navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(
            route = "information/{jobJson}",
            arguments = listOf(navArgument("jobJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val jobJson = backStackEntry.arguments?.getString("jobJson")
            val job = jobJson?.let { Json.decodeFromString<Job>(it) }

            DetailsScreen(navController = navController, job = job)
        }

    }
}

