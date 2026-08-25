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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchToCodeScreen(onBackClick: () -> Unit = {}) {
    var currentState by remember { mutableIntStateOf(0) }

    val darkBg = Color(0xFF0D0D0D)
    val cardBg = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00E5FF)
    val subText = Color(0xFF8E8E93)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sketch to Code", color = Color.White) },
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
                                Text("Point camera at your wireframe", color = subText)
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
                            Text("Pick from Gallery")
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        TextButton(onClick = { currentState = 1 }) {
                            Text("Try Demo", color = cyan)
                        }
                    }
                }
                1 -> {
                    LaunchedEffect(Unit) {
                        delay(2000)
                        currentState = 2
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = cyan)
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Analyzing wireframe...", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Detecting UI elements...", color = subText)
                        Text("Mapping layout structure...", color = subText)
                        Text("Generating Compose code...", color = subText)
                    }
                }
                2 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(cardBg, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Your Sketch (Placeholder)", color = subText)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Generated Code", color = Color.White, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.Black, RoundedCornerShape(8.dp))
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = """
@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome Back", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Email") })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Password") })
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {}) { Text("Sign In") }
    }
}
                                """.trimIndent(),
                                color = Color.Green,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.verticalScroll(rememberScrollState())
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { currentState = 0 },
                                colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Copy Code")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedButton(
                                onClick = { },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                            ) {
                                Text("Export")
                            }
                        }
                    }
                }
            }
        }
    }
}
