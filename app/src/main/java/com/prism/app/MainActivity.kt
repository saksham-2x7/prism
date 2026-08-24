package com.prism.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.prism.app.data.PreferencesManager
import com.prism.app.ui.navigation.PrismNavHost
import com.prism.app.ui.theme.PrismTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val preferencesManager = PreferencesManager(this)
        
        setContent {
            val systemTheme = isSystemInDarkTheme()
            val isDarkThemePref by preferencesManager.isDarkThemeFlow.collectAsState(initial = systemTheme)
            val isLoggedIn by preferencesManager.isLoggedInFlow.collectAsState(initial = false)
            
            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            
            PrismTheme(darkTheme = isDarkThemePref) {
                PrismNavHost(
                    navController = navController,
                    isDarkTheme = isDarkThemePref,
                    onToggleTheme = {
                        coroutineScope.launch {
                            preferencesManager.setDarkTheme(!isDarkThemePref)
                        }
                    },
                    startDestination = if (isLoggedIn) "home" else "login"
                )
            }
        }
    }
}
