package com.example.mycomposepractice.navigation

import LoginScreen
import SelectAuthScreen
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.*
import com.example.mycomposepractice.PreAuthScreen
import com.example.mycomposepractice.screens.HomeScreen
import com.example.mycomposepractice.ui.PreAuthFlowScreen
import com.google.android.gms.location.LocationServices

@Composable
fun AppNavigation(){

    val navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            SelectAuthScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("preAuth") {
            PreAuthScreen(navController)
        }

        composable(
            route = "selectPayment/{amount}"
        ) { backStackEntry ->

            val amount =
                backStackEntry.arguments?.getString("amount") ?: "0"

            PreAuthFlowScreen(
                navController = navController,
                amount = amount
            )
        }
    }

}