package com.prism.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prism.app.ui.components.FeatureCard

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0F0F0F),
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* already here */ },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00E5FF),
                        selectedTextColor = Color(0xFF00E5FF),
                        unselectedIconColor = Color(0xFF6E6E73),
                        unselectedTextColor = Color(0xFF6E6E73),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("home") },
                    icon = { Icon(Icons.Filled.Build, contentDescription = "Tools") },
                    label = { Text("Tools") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00E5FF),
                        selectedTextColor = Color(0xFF00E5FF),
                        unselectedIconColor = Color(0xFF6E6E73),
                        unselectedTextColor = Color(0xFF6E6E73),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("privacy_vault") },
                    icon = { Icon(Icons.Outlined.History, contentDescription = "History") },
                    label = { Text("History") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00E5FF),
                        selectedTextColor = Color(0xFF00E5FF),
                        unselectedIconColor = Color(0xFF6E6E73),
                        unselectedTextColor = Color(0xFF6E6E73),
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF00E5FF),
                        selectedTextColor = Color(0xFF00E5FF),
                        unselectedIconColor = Color(0xFF6E6E73),
                        unselectedTextColor = Color(0xFF6E6E73),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                HomeTopBar(onAvatarClick = { navController.navigate("settings") })
            }
            item(span = { GridItemSpan(2) }) {
                HomeGreeting()
            }
            item(span = { GridItemSpan(2) }) {
                QuickStatsRow()
            }
            
            // Grid Items
            val features = listOf(
                FeatureItem("📸", "Sketch to Code", "Camera → Compose code", Color(0xFF00E5FF), "sketch_to_code"),
                FeatureItem("🎙️", "Voice to Code", "Speak → Working code", Color(0xFFBB86FC), "voice_to_code"),
                FeatureItem("🐛", "Bug Reporter", "Screenshot → Bug report", Color(0xFFFF5252), "bug_reporter"),
                FeatureItem("📝", "Notes to Tasks", "Handwriting → Dev tasks", Color(0xFFFFD600), "handwriting_to_tasks"),
                FeatureItem("🔍", "Code Explainer", "Import → Understand", Color(0xFF00E676), "code_explainer"),
                FeatureItem("🏗️", "Architecture", "Code → Diagrams", Color(0xFF00E5FF), "architecture_diagram"),
                FeatureItem("🔐", "Privacy Vault", "Encrypted history", Color(0xFFBB86FC), "privacy_vault")
            )
            
            items(features) { feature ->
                FeatureCard(
                    emoji = feature.emoji,
                    title = feature.title,
                    subtitle = feature.subtitle,
                    accentColor = feature.color,
                    onClick = { navController.navigate(feature.route) }
                )
            }
            
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "100% On-Device • Zero Cloud • NPU Accelerated",
                        color = Color(0xFF6E6E73),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

data class FeatureItem(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val color: Color,
    val route: String
)

@Composable
fun HomeTopBar(onAvatarClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "NEURON",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        IconButton(
            onClick = onAvatarClick,
            modifier = Modifier
                .size(40.dp)
                .border(2.dp, Color(0xFF00E5FF), CircleShape)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A))
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Settings",
                tint = Color.White
            )
        }
    }
}

@Composable
fun HomeGreeting() {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(
            text = "Hello, Developer \uD83D\uDC4B",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Monday, Aug 25",
            color = Color(0xFFB0B0B0),
            fontSize = 16.sp
        )
    }
}

@Composable
fun QuickStatsRow() {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(title = "7 Tools", dotColor = Color(0xFF00E5FF))
        StatCard(title = "On-Device", dotColor = Color(0xFFBB86FC))
        StatCard(title = "NPU Ready", dotColor = Color(0xFF00E676))
    }
}

@Composable
fun StatCard(title: String, dotColor: Color) {
    Surface(
        modifier = Modifier
            .width(100.dp)
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1A1A1A)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(dotColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
