package com.prism.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val BgColor = Color(0xFF000000)
private val CardColor = Color(0xFF1A1A1A)
private val CardBorderColor = Color(0xFF2A2A2A)
private val TextPrimaryColor = Color.White
private val TextSecondaryColor = Color(0xFFB0B0B0)
private val AccentCyanColor = Color(0xFF00E5FF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SketchToCodeScreen(onBackClick: () -> Unit = {}) {
    var state by remember { mutableIntStateOf(0) }

    LaunchedEffect(state) {
        if (state == 1) {
            delay(2000)
            state = 2
        }
    }

    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = { Text("Sketch to Code", color = TextPrimaryColor, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimaryColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgColor)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = state, label = "State Transition") { targetState ->
                when (targetState) {
                    0 -> IdleState(onCapture = { state = 1 })
                    1 -> ProcessingState()
                    2 -> ResultState(onNewScan = { state = 0 })
                }
            }
        }
    }
}

@Composable
private fun IdleState(onCapture: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera", tint = TextSecondaryColor, modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(16.dp))
                    Text("Point at your wireframe sketch", color = TextSecondaryColor, fontSize = 16.sp)
                }
            }
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onCapture,
            colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Capture", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))
        }
    }
}

@Composable
private fun ProcessingState() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse),
        label = "pulseAlpha"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            border = BorderStroke(2.dp, AccentCyanColor.copy(alpha = alpha)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = AccentCyanColor)
                    Spacer(Modifier.height(24.dp))
                    Text("Analyzing wireframe...", color = AccentCyanColor, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ResultState(onNewScan: () -> Unit) {
    val demoCode = """
@Composable
fun LoginPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.logo))
        OutlinedTextField(value = email, label = { Text("Email") })
        OutlinedTextField(value = password, label = { Text("Password") })
        Button(onClick = { login() }) { Text("Sign In") }
    }
}
    """.trimIndent()

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0D0D)),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())) {
                Text(demoCode, color = AccentCyanColor, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
            }
        }
        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            OutlinedButton(
                onClick = {},
                border = BorderStroke(1.dp, AccentCyanColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentCyanColor)
            ) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
                Spacer(Modifier.width(8.dp))
                Text("Copy Code")
            }
            Button(
                onClick = onNewScan,
                colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "New Scan")
                Spacer(Modifier.width(8.dp))
                Text("New Scan", fontWeight = FontWeight.Bold)
            }
        }
    }
}
