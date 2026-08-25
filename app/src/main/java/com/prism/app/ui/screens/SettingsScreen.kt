package com.prism.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val BgBlack = Color(0xFF000000)
private val CardBg = Color(0xFF1A1A1A)
private val CardBorder = Color(0xFF2A2A2A)
private val AccentCyan = Color(0xFF00E5FF)
private val AccentViolet = Color(0xFFBB86FC)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFB0B0B0)
private val TextTertiary = Color(0xFF6E6E73)
private val ErrorRed = Color(0xFFFF5252)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var isDarkTheme by remember { mutableStateOf(true) }
    var isNpuEnabled by remember { mutableStateOf(true) }
    var isCloudSync by remember { mutableStateOf(false) }
    var selectedExportFormat by remember { mutableStateOf("Kotlin") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgBlack)
            )
        },
        containerColor = BgBlack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Profile Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(20.dp),
                color = CardBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(AccentCyan.copy(alpha = 0.15f))
                            .border(2.dp, AccentCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("D", color = AccentCyan, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Developer", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Guest Account", color = TextSecondary, fontSize = 14.sp)
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AccentCyan.copy(alpha = 0.1f)
                    ) {
                        Text(
                            "PRO",
                            color = AccentCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Appearance Section
            SectionHeader("Appearance")

            SettingsToggleItem(
                icon = Icons.Default.DarkMode,
                title = "Dark Theme",
                subtitle = "Always use dark mode",
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it },
                accentColor = AccentViolet
            )

            // AI & Performance Section
            SectionHeader("AI & Performance")

            SettingsToggleItem(
                icon = Icons.Default.Memory,
                title = "NPU Acceleration",
                subtitle = "Use Neural Processing Unit for faster inference",
                checked = isNpuEnabled,
                onCheckedChange = { isNpuEnabled = it },
                accentColor = AccentCyan
            )

            SettingsNavigationItem(
                icon = Icons.Default.Tune,
                title = "Model Settings",
                subtitle = "Gemma 3n E2B • Quantized INT4",
                onClick = { }
            )

            // Data & Cloud Section
            SectionHeader("Data & Cloud")

            SettingsToggleItem(
                icon = Icons.Default.CloudSync,
                title = "Cloud Sync",
                subtitle = "Backup history to Google Drive",
                checked = isCloudSync,
                onCheckedChange = { isCloudSync = it },
                accentColor = AccentCyan
            )

            SettingsNavigationItem(
                icon = Icons.Default.Code,
                title = "Export Format",
                subtitle = selectedExportFormat,
                onClick = {
                    selectedExportFormat = when (selectedExportFormat) {
                        "Kotlin" -> "Swift"
                        "Swift" -> "Python"
                        "Python" -> "Dart"
                        else -> "Kotlin"
                    }
                }
            )

            SettingsNavigationItem(
                icon = Icons.Default.Storage,
                title = "Local Storage",
                subtitle = "158 MB used • 12 sessions cached",
                onClick = { }
            )

            // About Section
            SectionHeader("About")

            SettingsNavigationItem(
                icon = Icons.Default.Info,
                title = "Version",
                subtitle = "Neuron v2.0.0 • Build 2026.08.25",
                onClick = { }
            )

            SettingsNavigationItem(
                icon = Icons.Default.Shield,
                title = "Privacy Policy",
                subtitle = "100% on-device processing",
                onClick = { }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Out Button
            OutlinedButton(
                onClick = { navController.navigate("login") { popUpTo(0) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sign Out", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer
            Text(
                text = "Made with \u2764\ufe0f for iQOO Hackathon 2026",
                color = TextTertiary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title.uppercase(),
        color = TextTertiary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
    )
}

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    accentColor: Color = AccentCyan
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        color = CardBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(subtitle, color = TextSecondary, fontSize = 13.sp)
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = accentColor,
                    uncheckedThumbColor = TextSecondary,
                    uncheckedTrackColor = CardBorder
                )
            )
        }
    }
}

@Composable
private fun SettingsNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = CardBg,
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AccentCyan.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(subtitle, color = TextSecondary, fontSize = 13.sp)
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
