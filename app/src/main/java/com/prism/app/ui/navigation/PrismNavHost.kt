package com.prism.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun PrismNavHost(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    startDestination: String = "login"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable("login") { PlaceholderScreen("Login Screen") }
        composable("home") { PlaceholderScreen("Home Screen") }
        composable("sketch_to_code") { PlaceholderScreen("Sketch to Code Screen") }
        composable("voice_to_code") { PlaceholderScreen("Voice to Code Screen") }
        composable("bug_reporter") { PlaceholderScreen("Bug Reporter Screen") }
        composable("handwriting_to_tasks") { PlaceholderScreen("Handwriting to Tasks Screen") }
        composable("code_explainer") { PlaceholderScreen("Code Explainer Screen") }
        composable("architecture_diagram") { PlaceholderScreen("Architecture Diagram Screen") }
        composable("privacy_vault") { PlaceholderScreen("Privacy Vault Screen") }
        composable("settings") { PlaceholderScreen("Settings Screen") }
    }
}

@Composable
fun PlaceholderScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = name)
    }
}
