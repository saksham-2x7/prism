package com.prism.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
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

private val BgColor = Color(0xFF000000)
private val CardColor = Color(0xFF1A1A1A)
private val CardBorderColor = Color(0xFF2A2A2A)
private val TextPrimaryColor = Color.White
private val TextSecondaryColor = Color(0xFFB0B0B0)
private val AccentCyanColor = Color(0xFF00E5FF)
private val SuccessColor = Color(0xFF00E676)
private val WarningColor = Color(0xFFFFD600)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeExplainerScreen(onBackClick: () -> Unit = {}) {
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
                title = { Text("Code Explainer", color = TextPrimaryColor, fontWeight = FontWeight.Bold) },
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
                    0 -> IdleState(onExplain = { state = 1 })
                    1 -> ProcessingState()
                    2 -> ResultState(onNewAnalysis = { state = 0 })
                }
            }
        }
    }
}

@Composable
private fun IdleState(onExplain: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Paste or import code", color = TextSecondaryColor, fontFamily = FontFamily.Monospace) },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CardColor,
                unfocusedContainerColor = CardColor,
                focusedBorderColor = AccentCyanColor,
                unfocusedBorderColor = CardBorderColor,
                cursorColor = AccentCyanColor
            ),
            textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, color = TextPrimaryColor)
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onExplain,
            colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Explain Code", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }
    }
}

@Composable
private fun ProcessingState() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(color = AccentCyanColor, trackColor = CardBorderColor, modifier = Modifier.fillMaxWidth(0.6f))
        Spacer(Modifier.height(24.dp))
        Text("Analyzing code structure...", color = AccentCyanColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
private fun ResultState(onNewAnalysis: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                Text("Summary", color = TextPrimaryColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                Text("This function implements the binary search algorithm to find an element in a sorted array efficiently.", color = TextSecondaryColor, fontSize = 14.sp)
                
                Spacer(Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Complexity:", color = TextPrimaryColor, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(SuccessColor.copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text("O(log n)", color = SuccessColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text("Key Concepts", color = TextPrimaryColor, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ChipTag("Binary Search")
                    ChipTag("Divide & Conquer")
                }

                Spacer(Modifier.height(24.dp))
                Text("Code Smells & Suggestions", color = TextPrimaryColor, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth().background(WarningColor.copy(alpha = 0.1f)).border(1.dp, WarningColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp)).padding(12.dp)) {
                    Text("Consider handling integer overflow when calculating the mid index using 'left + (right - left) / 2'.", color = WarningColor, fontSize = 14.sp)
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onNewAnalysis,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "New Analysis")
            Spacer(Modifier.width(8.dp))
            Text("New Analysis", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ChipTag(text: String) {
    Box(
        modifier = Modifier
            .border(1.dp, AccentCyanColor, RoundedCornerShape(16.dp))
            .background(CardColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = AccentCyanColor, fontSize = 12.sp)
    }
}
