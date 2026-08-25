package com.prism.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val DarkBackground = Color(0xFF0D0D0D)
private val CardSurface = Color(0xFF1A1A1A)
private val AccentCyan = Color(0xFF00E5FF)
private val AccentViolet = Color(0xFFBB86FC)
private val SuccessGreen = Color(0xFF00E676)
private val WarningAmber = Color(0xFFFFD600)
private val ErrorRed = Color(0xFFFF5252)
private val SubtitleText = Color(0xFF8E8E93)

data class Feature(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val accentColor: Color,
    val route: String
)

@Composable
fun HomeScreen(navController: NavController) {
    val features = listOf(
        Feature("📸", "Sketch to Code", "Camera → Compose code", AccentCyan, "sketch_to_code"),
        Feature("🎙️", "Voice to Code", "Speak → Working code", AccentViolet, "voice_to_code"),
        Feature("🐛", "Bug Reporter", "Screenshot → Bug report", ErrorRed, "bug_reporter"),
        Feature("📝", "Notes to Tasks", "Handwriting → Dev tasks", WarningAmber, "handwriting_to_tasks"),
        Feature("🔍", "Code Explainer", "Import → Understand", SuccessGreen, "code_explainer"),
        Feature("🏗️", "Architecture", "Code → Diagrams", AccentCyan, "architecture_diagram"),
        Feature("🔐", "Privacy Vault", "Encrypted history", AccentViolet, "privacy_vault")
    )

    val currentDate = SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🔱 Neuron",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            IconButton(onClick = { /* Toggle theme */ }) {
                Icon(
                    imageVector = Icons.Default.Nightlight,
                    contentDescription = "Toggle Theme",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Greeting
        Text(
            text = "Hello, Developer 👋",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = currentDate,
            color = SubtitleText,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(features) { feature ->
                FeatureCard(
                    emoji = feature.emoji,
                    title = feature.title,
                    subtitle = feature.subtitle,
                    accentColor = feature.accentColor,
                    onClick = { navController.navigate(feature.route) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer
        Text(
            text = "100% On-Device • Zero Cloud • NPU Accelerated",
            color = SubtitleText,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
fun FeatureCard(
    emoji: String,
    title: String,
    subtitle: String,
    accentColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = CardSurface,
        border = BorderStroke(1.dp, Color(0xFF2A2A2A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = emoji,
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = SubtitleText,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
