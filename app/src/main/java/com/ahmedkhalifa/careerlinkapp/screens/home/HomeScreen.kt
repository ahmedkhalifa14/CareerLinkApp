package com.ahmedkhalifa.careerlinkapp.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmedkhalifa.careerlinkapp.composable.getColor
import com.ahmedkhalifa.careerlinkapp.graphs.HomeNavGraph
import com.ahmedkhalifa.careerlinkapp.graphs.ProfileGraph
import com.ahmedkhalifa.careerlinkapp.ui.theme.AppColors

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val listState = rememberLazyListState()

    // Track if the user is scrolling down
    val isScrollingDown = rememberScrollDirection(listState)

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                isVisible = !isScrollingDown // Hide Bottom Bar when scrolling down
            )
        }
    ) {
        HomeNavGraph(navController = navController, listState = listState)
    }
}

@Composable
fun BottomBar(navController: NavHostController, isVisible: Boolean) {
    val bottomBarBackgroundColor = getColor(AppColors.AppColorSet.AppScreenBackgroundColor)

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Settings,
        BottomBarScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any {
        it.route == currentDestination?.route || currentDestination?.route?.startsWith(
            ProfileGraph.Profile.route
        ) == true
    }

    // Use AnimatedVisibility to control bottom bar visibility
    AnimatedVisibility(
        visible = bottomBarDestination && isVisible,
        enter = slideInVertically(initialOffsetY = { it }), // Slide in from the bottom
        exit = slideOutVertically(targetOffsetY = { it })   // Slide out to the bottom
    ) {
        NavigationBar(
            modifier = Modifier.height(56.dp),
            containerColor = bottomBarBackgroundColor,
            tonalElevation = NavigationBarDefaults.Elevation,
            windowInsets = NavigationBarDefaults.windowInsets,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val iconColor = getColor(AppColors.AppColorSet.AppIconColor)
    val textColor = getColor(AppColors.AppColorSet.AppMainTextColor)
    BottomNavigationItem(
        label = {
            Text(text = stringResource(screen.title), color = textColor)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon",
                tint = iconColor
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}


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
