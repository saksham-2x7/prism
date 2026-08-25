package com.prism.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
private val ErrorColor = Color(0xFFFF5252)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BugReporterScreen(onBackClick: () -> Unit = {}) {
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
                title = { Text("Bug Reporter", color = TextPrimaryColor, fontWeight = FontWeight.Bold) },
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
                    2 -> ResultState(onNewReport = { state = 0 })
                }
            }
        }
    }
}

@Composable
private fun IdleState(onCapture: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("Capture or import a screenshot", color = TextSecondaryColor, fontSize = 16.sp)
            }
        }
        Spacer(Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = onCapture,
                colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Camera", fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = onCapture,
                border = BorderStroke(1.dp, AccentCyanColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentCyanColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Gallery", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ProcessingState() {
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val yOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(animation = tween(1500, easing = LinearEasing), repeatMode = RepeatMode.Restart),
        label = "scanLine"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardColor)
            .border(1.dp, CardBorderColor, RoundedCornerShape(16.dp))) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .offset(y = yOffset.dp)
                .background(AccentCyanColor)
            )
        }
        Spacer(Modifier.height(24.dp))
        Text("Analyzing UI for issues...", color = AccentCyanColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
private fun ResultState(onNewReport: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(ErrorColor.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text("🔴 High", color = ErrorColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Text("UI Overlap in Landscape", color = TextPrimaryColor, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Spacer(Modifier.height(16.dp))
                Text("Description", color = AccentCyanColor, fontWeight = FontWeight.Bold)
                Text("The primary action button overlaps with the bottom navigation bar when the device is rotated to landscape mode.", color = TextSecondaryColor)
                Spacer(Modifier.height(16.dp))
                Text("Steps to Reproduce", color = AccentCyanColor, fontWeight = FontWeight.Bold)
                Text("1. Open the app on a phone.\n2. Navigate to Dashboard.\n3. Rotate device to landscape.", color = TextSecondaryColor)
                Spacer(Modifier.height(16.dp))
                Text("Expected vs Actual", color = AccentCyanColor, fontWeight = FontWeight.Bold)
                Text("Expected: Button remains accessible above the nav bar.\nActual: Button is partially obscured by the nav bar.", color = TextSecondaryColor)
                Spacer(Modifier.height(16.dp))
                Text("Device Info", color = AccentCyanColor, fontWeight = FontWeight.Bold)
                Text("Model: Pixel 7 Pro\nOS: Android 14\nScreen Density: 420dpi", color = TextSecondaryColor)
            }
        }
        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            OutlinedButton(
                onClick = {},
                border = BorderStroke(1.dp, AccentCyanColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentCyanColor)
            ) {
                Icon(Icons.Default.Share, contentDescription = "Export")
                Spacer(Modifier.width(8.dp))
                Text("Export Report")
            }
            Button(
                onClick = onNewReport,
                colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "New Report")
                Spacer(Modifier.width(8.dp))
                Text("New Report", fontWeight = FontWeight.Bold)
            }
        }
    }
}
