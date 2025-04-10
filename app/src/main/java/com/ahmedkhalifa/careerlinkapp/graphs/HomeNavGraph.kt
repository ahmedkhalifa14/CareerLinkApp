package com.ahmedkhalifa.careerlinkapp.graphs

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ahmedkhalifa.careerlinkapp.screens.profile.ProfileScreen
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
fun HomeNavGraph(navController: NavHostController, listState: LazyListState) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomePage(
                navigationController = navController,
                listState = listState,
                isScrollingDown = rememberScrollDirection(listState) // Pass scroll state
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
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

            // Pass the job data to the DetailsScreen
            DetailsScreen(navController = navController, job = job)
        }
    }
}

// Scroll direction detection
@Composable
fun rememberScrollDirection(listState: LazyListState): Boolean {
    var lastScrollOffset by remember { mutableStateOf(0) }
    var lastIndex by remember { mutableStateOf(0) }

    return remember {
        derivedStateOf {
            val isScrollingDown = when {
                listState.firstVisibleItemIndex > lastIndex -> true
                listState.firstVisibleItemIndex < lastIndex -> false
                else -> listState.firstVisibleItemScrollOffset > lastScrollOffset
            }

            lastIndex = listState.firstVisibleItemIndex
            lastScrollOffset = listState.firstVisibleItemScrollOffset

            isScrollingDown
        }
    }.value
}
