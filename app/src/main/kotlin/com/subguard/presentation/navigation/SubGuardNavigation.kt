package com.subguard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.subguard.presentation.screens.add_edit.AddEditSubscriptionScreen
import com.subguard.presentation.screens.home.HomeScreen
import com.subguard.presentation.screens.settings.SettingsScreen

object Screen {
    const val HOME = "home_screen"
    const val ADD_EDIT = "add_edit_screen"
    const val SETTINGS = "settings_screen"
}

@Composable
fun SubGuardNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HOME
    ) {
        composable(Screen.HOME) {
            HomeScreen(navController = navController)
        }
        composable(Screen.ADD_EDIT) {
            AddEditSubscriptionScreen(navController = navController)
        }
        composable(Screen.SETTINGS) {
            SettingsScreen(navController = navController)
        }
    }
}
