package com.prism.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandwritingToTasksScreen(onBackClick: () -> Unit = {}) {
    var currentState by remember { mutableIntStateOf(0) }

    val darkBg = Color(0xFF0D0D0D)
    val cardBg = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00E5FF)
    val red = Color(0xFFFF5252)
    val amber = Color(0xFFFFD600)
    val green = Color(0xFF00E676)
    val subText = Color(0xFF8E8E93)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Handwriting to Tasks", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBg)
            )
        },
        containerColor = darkBg
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            when (currentState) {
                0 -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .border(2.dp, subText, RoundedCornerShape(12.dp))
                                .background(cardBg, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = subText, modifier = Modifier.size(48.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Photograph your meeting notes", color = subText)
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Take Photo")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Text("Pick Image")
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        TextButton(onClick = { currentState = 1 }) {
                            Text("Try Demo", color = cyan)
                        }
                    }
                }
                1 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text("Extracted Text", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = cardBg),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "\"Fix login crash on 14, add dark mode toggle, refactor payment, deploy v2.1 by Friday\"",
                                modifier = Modifier.padding(16.dp),
                                color = subText,
                                fontStyle = FontStyle.Italic
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Generated Tasks", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            TaskCard("Fix login crash on Android 14", "Bug \uD83D\uDC1B", red, "P0", "Android Lead", cardBg, subText)
                            TaskCard("Add dark mode toggle", "Feature ✨", cyan, "P2", "UI Dev", cardBg, subText)
                            TaskCard("Refactor payment module", "Tech Debt \uD83D\uDD27", subText, "P3", "Backend", cardBg, subText)
                            TaskCard("Deploy v2.1 by Friday", "Release \uD83D\uDCE6", green, "P1", "DevOps", cardBg, subText)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { currentState = 0 },
                            colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Export All")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(title: String, type: String, typeColor: Color, priority: String, assignee: String, cardBg: Color, subText: Color) {
    Card(colors = CardDefaults.cardColors(containerColor = cardBg), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(color = typeColor.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp), border = border(typeColor)) {
                    Text(type, color = typeColor, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), style = MaterialTheme.typography.labelSmall)
                }
                Surface(color = Color.DarkGray, shape = RoundedCornerShape(16.dp)) {
                    Text(priority, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(assignee, color = subText, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun border(color: Color) = androidx.compose.foundation.BorderStroke(1.dp, color)
