package com.prism.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeExplainerScreen(onBackClick: () -> Unit = {}) {
    var currentState by remember { mutableIntStateOf(0) }

    val darkBg = Color(0xFF0D0D0D)
    val cardBg = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00E5FF)
    val red = Color(0xFFFF5252)
    val amber = Color(0xFFFFD600)
    val subText = Color(0xFF8E8E93)
    val violet = Color(0xFFBB86FC)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Code Explainer", color = Color.White) },
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
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.InsertDriveFile, contentDescription = null, modifier = Modifier.size(32.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Import code file")
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("OR", color = subText)
                        Spacer(modifier = Modifier.height(24.dp))
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth().height(200.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = subText,
                                focusedBorderColor = cyan,
                                unfocusedTextColor = Color.White,
                                focusedTextColor = Color.White
                            ),
                            placeholder = { Text("Paste Code", color = subText) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Auto-detect language", color = subText, style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(32.dp))
                        TextButton(onClick = { currentState = 1 }) {
                            Text("Try Demo", color = cyan)
                        }
                    }
                }
                1 -> {
                    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                        Text("Analysis Result", color = Color.White, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Card(colors = CardDefaults.cardColors(containerColor = cardBg), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("📖 Summary", color = Color.White, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("This function implements a binary search algorithm on a sorted array. It returns the index of the target element or -1 if not found.", color = subText)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Card(colors = CardDefaults.cardColors(containerColor = cardBg), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("⚡ Complexity", color = Color.White, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Surface(color = cyan.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp)) {
                                        Text("Time: O(log n)", color = cyan, modifier = Modifier.padding(8.dp))
                                    }
                                    Surface(color = amber.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp)) {
                                        Text("Space: O(1)", color = amber, modifier = Modifier.padding(8.dp))
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(colors = CardDefaults.cardColors(containerColor = cardBg), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("🔴 Code Smells", color = red, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("• Magic number 42 on line 7", color = subText)
                                Text("• Unused parameter 'context'", color = subText)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(colors = CardDefaults.cardColors(containerColor = cardBg), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("💡 Improvements", color = Color.White, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(modifier = Modifier.fillMaxWidth().background(Color.Black, RoundedCornerShape(8.dp)).padding(12.dp)) {
                                    Text(
                                        text = """
fun binarySearch(arr: IntArray, target: Int): Int {
    var left = 0
    var right = arr.size - 1
    // ...
}
                                        """.trimIndent(),
                                        color = violet,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { currentState = 0 },
                            colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Analyze Another")
                        }
                    }
                }
            }
        }
    }
}
