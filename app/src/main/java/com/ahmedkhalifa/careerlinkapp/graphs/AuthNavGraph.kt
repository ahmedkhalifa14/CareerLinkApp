package com.ahmedkhalifa.careerlinkapp.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ahmedkhalifa.careerlinkapp.ScreenContent
import com.ahmedkhalifa.careerlinkapp.screens.login.LoginScreen
import com.ahmedkhalifa.careerlinkapp.screens.onboarding.OnboardingScreen
import com.ahmedkhalifa.careerlinkapp.screens.register.RegisterScreen
import com.ahmedkhalifa.careerlinkapp.screens.register.UserFormScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SignUp.route
    ) {
        composable(route = AuthScreen.SignUp.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = AuthScreen.Forgot.route) {
            ScreenContent(name = AuthScreen.Forgot.route) {}
        }
        composable(route=AuthScreen.OnBoarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(
            route = "USER_FORM/{userEmail}/{userPassword}",
            arguments = listOf(
                navArgument("userEmail") { type = NavType.StringType },
                navArgument("userPassword") { type = NavType.StringType }
            )
        ) {
            UserFormScreen(navController, it)
        }

    }
}

sealed class AuthScreen(val route: String) {
    data object OnBoarding : AuthScreen(route = "ON_BOARDING")
    data object Login : AuthScreen(route = "LOGIN")
    data object SignUp : AuthScreen(route = "SIGN_UP")
    data object Forgot : AuthScreen(route = "FORGOT")
    data object UserForm : AuthScreen(route = "USER_FORM")
}