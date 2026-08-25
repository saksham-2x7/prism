package com.prism.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prism.app.ui.screens.*

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
        composable("login") { LoginScreen(navController = navController) }
        composable("home") { HomeScreen(navController = navController) }
        composable("sketch_to_code") { SketchToCodeScreen(onBackClick = { navController.popBackStack() }) }
        composable("voice_to_code") { VoiceToCodeScreen(onBackClick = { navController.popBackStack() }) }
        composable("bug_reporter") { BugReporterScreen(onBackClick = { navController.popBackStack() }) }
        composable("handwriting_to_tasks") { HandwritingToTasksScreen(onBackClick = { navController.popBackStack() }) }
        composable("code_explainer") { CodeExplainerScreen(onBackClick = { navController.popBackStack() }) }
        composable("architecture_diagram") { ArchitectureDiagramScreen(onBackClick = { navController.popBackStack() }) }
        composable("privacy_vault") { PrivacyVaultScreen(onBackClick = { navController.popBackStack() }) }
        composable("settings") { SettingsScreen(navController = navController) }
    }
}
