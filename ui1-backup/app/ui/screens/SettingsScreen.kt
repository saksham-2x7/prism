package com.prism.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val DarkBackground = Color(0xFF0D0D0D)
private val CardSurface = Color(0xFF1A1A1A)
private val ErrorRed = Color(0xFFFF5252)
private val SubtitleText = Color(0xFF8E8E93)
private val AccentCyan = Color(0xFF00E5FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var darkModeEnabled by remember { mutableStateOf(true) }
    var npuAccelerationEnabled by remember { mutableStateOf(true) }
    var autoSaveEnabled by remember { mutableStateOf(true) }
    var exportFormatExpanded by remember { mutableStateOf(false) }
    var selectedExportFormat by remember { mutableStateOf("Markdown") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground
                )
            )
        },
        containerColor = DarkBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(AccentCyan),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "D",
                        color = DarkBackground,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Developer",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "developer@prism.app",
                        color = SubtitleText,
                        fontSize = 14.sp
                    )
                }
            }

            HorizontalDivider(color = CardSurface, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Settings Items
            SettingsSwitchItem(
                icon = Icons.Default.DarkMode,
                label = "Dark Mode",
                checked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )

            SettingsSwitchItem(
                icon = Icons.Default.Memory,
                label = "NPU Acceleration",
                description = "Faster on-device ML processing",
                checked = npuAccelerationEnabled,
                onCheckedChange = { npuAccelerationEnabled = it }
            )

            SettingsSwitchItem(
                icon = Icons.Default.Save,
                label = "Auto-save results",
                checked = autoSaveEnabled,
                onCheckedChange = { autoSaveEnabled = it }
            )

            // Export Format Dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                SettingsClickableItem(
                    icon = Icons.Default.Description,
                    label = "Export format",
                    value = selectedExportFormat,
                    onClick = { exportFormatExpanded = true }
                )
                DropdownMenu(
                    expanded = exportFormatExpanded,
                    onDismissRequest = { exportFormatExpanded = false },
                    modifier = Modifier.background(CardSurface)
                ) {
                    listOf("Markdown", "PDF", "HTML").forEach { format ->
                        DropdownMenuItem(
                            text = { Text(format, color = Color.White) },
                            onClick = {
                                selectedExportFormat = format
                                exportFormatExpanded = false
                            }
                        )
                    }
                }
            }

            SettingsActionItem(
                icon = Icons.Default.Delete,
                label = "Clear history",
                description = "Delete all saved scans and tasks",
                contentColor = ErrorRed,
                onClick = { /* Clear history */ }
            )

            SettingsActionItem(
                icon = Icons.Default.Info,
                label = "About PRISM",
                description = "Version 1.0.0 • iQOO Hackathon 2026",
                contentColor = Color.White,
                showChevron = true,
                onClick = { /* About */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = { /* Sign out */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
            ) {
                Text("Sign Out", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    label: String,
    description: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Color.White)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, color = Color.White, fontSize = 16.sp)
            if (description != null) {
                Text(text = description, color = SubtitleText, fontSize = 12.sp)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = DarkBackground,
                checkedTrackColor = AccentCyan
            )
        )
    }
}

@Composable
fun SettingsClickableItem(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Color.White)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Text(text = value, color = SubtitleText, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = SubtitleText)
    }
}

@Composable
fun SettingsActionItem(
    icon: ImageVector,
    label: String,
    description: String? = null,
    contentColor: Color,
    showChevron: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = contentColor)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, color = contentColor, fontSize = 16.sp)
            if (description != null) {
                Text(text = description, color = SubtitleText, fontSize = 12.sp)
            }
        }
        if (showChevron) {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = SubtitleText)
        }
    }
}
