package com.prism.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchitectureDiagramScreen(onBackClick: () -> Unit = {}) {
    var currentState by remember { mutableIntStateOf(0) }

    val darkBg = Color(0xFF0D0D0D)
    val cardBg = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00E5FF)
    val subText = Color(0xFF8E8E93)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Architecture Diagram", color = Color.White) },
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
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                        ) {
                            Text("Import Project Folder")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Or select individual files", color = subText)
                        Spacer(modifier = Modifier.height(32.dp))
                        TextButton(onClick = { currentState = 1 }) {
                            Text("Try Demo", color = cyan)
                        }
                    }
                }
                1 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Text("Module Map", color = cyan, fontWeight = FontWeight.Bold)
                            Text("Class Diagram", color = subText)
                            Text("Data Flow", color = subText)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(32.dp))

                        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val centerX = size.width / 2
                                val topY = 100f
                                val midY = 300f
                                val bottomY = 500f
                                val bottomY2 = 700f

                                drawLine(color = subText, start = Offset(centerX, topY + 50f), end = Offset(centerX, midY - 50f), strokeWidth = 4f)
                                drawLine(color = subText, start = Offset(centerX, midY + 50f), end = Offset(centerX, bottomY - 50f), strokeWidth = 4f)
                                drawLine(color = subText, start = Offset(centerX, bottomY + 50f), end = Offset(centerX, bottomY2 - 50f), strokeWidth = 4f)
                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                DiagramBox("UI Layer", cyan)
                                DiagramBox("ViewModel", Color(0xFFBB86FC))
                                DiagramBox("Repository", Color(0xFF00E676))
                                DiagramBox("API / DB", Color(0xFFFFD600))
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(
                                onClick = { currentState = 0 },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                            ) {
                                Text("Export as PNG")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = { currentState = 0 },
                                colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Export Mermaid")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DiagramBox(text: String, borderColor: Color) {
    Box(
        modifier = Modifier
            .width(160.dp)
            .height(60.dp)
            .background(Color(0xFF1A1A1A), RoundedCornerShape(8.dp))
            .border(2.dp, borderColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
