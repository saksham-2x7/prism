package com.prism.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

private val BgColor = Color(0xFF000000)
private val CardColor = Color(0xFF1A1A1A)
private val CardBorderColor = Color(0xFF2A2A2A)
private val TextPrimaryColor = Color.White
private val TextSecondaryColor = Color(0xFFB0B0B0)
private val AccentCyanColor = Color(0xFF00E5FF)
private val AccentVioletColor = Color(0xFFBB86FC)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceToCodeScreen(onBackClick: () -> Unit = {}) {
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
                title = { Text("Voice to Code", color = TextPrimaryColor, fontWeight = FontWeight.Bold) },
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
                    0 -> IdleState(onRecord = { state = 1 })
                    1 -> ProcessingState()
                    2 -> ResultState(onRecordAgain = { state = 0 })
                }
            }
        }
    }
}

@Composable
private fun IdleState(onRecord: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse),
        label = "pulseScale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Restart),
        label = "pulseAlpha"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(160.dp * scale)
                    .clip(CircleShape)
                    .background(AccentCyanColor.copy(alpha = alpha))
            )
            IconButton(
                onClick = onRecord,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(AccentCyanColor)
            ) {
                Icon(Icons.Default.Mic, contentDescription = "Mic", tint = BgColor, modifier = Modifier.size(64.dp))
            }
        }
        Spacer(Modifier.height(32.dp))
        Text("Tap to start recording", color = TextSecondaryColor, fontSize = 18.sp)
    }
}

@Composable
private fun ProcessingState() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            for (i in 0 until 8) {
                val infiniteTransition = rememberInfiniteTransition(label = "bar")
                val height by infiniteTransition.animateFloat(
                    initialValue = 10f,
                    targetValue = Random.nextInt(30, 80).toFloat(),
                    animationSpec = infiniteRepeatable(
                        animation = tween(Random.nextInt(300, 600)),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "barHeight"
                )
                Box(
                    modifier = Modifier
                        .width(12.dp)
                        .height(height.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(AccentCyanColor)
                )
            }
        }
        Spacer(Modifier.height(32.dp))
        Text("Listening...", color = AccentCyanColor, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("00:03", color = TextSecondaryColor, fontSize = 16.sp)
    }
}

@Composable
private fun ResultState(onRecordAgain: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Transcription", color = AccentVioletColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
                Text("\"Create a function that takes a list of integers and returns the top 3 largest values\"", color = TextPrimaryColor, fontSize = 16.sp)
            }
        }
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0D0D)),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                Text("fun topThree(list: List<Int>): List<Int> = list.sortedDescending().take(3)", color = AccentCyanColor, fontFamily = FontFamily.Monospace, fontSize = 14.sp)
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
                Text("Copy")
            }
            Button(
                onClick = onRecordAgain,
                colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Record Again")
                Spacer(Modifier.width(8.dp))
                Text("Record Again", fontWeight = FontWeight.Bold)
            }
        }
    }
}
