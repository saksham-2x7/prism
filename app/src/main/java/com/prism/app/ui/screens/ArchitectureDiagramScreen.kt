package com.prism.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
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
private val AccentVioletColor = Color(0xFFBB86FC)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchitectureDiagramScreen(onBackClick: () -> Unit = {}) {
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
                title = { Text("Architecture Diagram", color = TextPrimaryColor, fontWeight = FontWeight.Bold) },
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
                    0 -> IdleState(onGenerate = { state = 1 })
                    1 -> ProcessingState()
                    2 -> ResultState(onNewDiagram = { state = 0 })
                }
            }
        }
    }
}

@Composable
private fun IdleState(onGenerate: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Import project or paste code", color = TextSecondaryColor, fontSize = 16.sp)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().height(150.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CardColor,
                unfocusedContainerColor = CardColor,
                focusedBorderColor = AccentVioletColor,
                unfocusedBorderColor = CardBorderColor
            )
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onGenerate,
            colors = ButtonDefaults.buttonColors(containerColor = AccentVioletColor, contentColor = BgColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Generate Diagram", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }
    }
}

@Composable
private fun ProcessingState() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = AccentVioletColor)
        Spacer(Modifier.height(24.dp))
        Text("Mapping dependencies...", color = AccentVioletColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Composable
private fun ResultState(onNewDiagram: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = CardColor),
            border = BorderStroke(1.dp, CardBorderColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                DiagramCanvas()
            }
        }
        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            OutlinedButton(
                onClick = {},
                border = BorderStroke(1.dp, AccentVioletColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentVioletColor)
            ) {
                Icon(Icons.Default.Share, contentDescription = "Export")
                Spacer(Modifier.width(8.dp))
                Text("Export PNG")
            }
            Button(
                onClick = onNewDiagram,
                colors = ButtonDefaults.buttonColors(containerColor = AccentVioletColor, contentColor = BgColor)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "New Diagram")
                Spacer(Modifier.width(8.dp))
                Text("New Diagram", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DiagramCanvas() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Box(modifier = Modifier.background(AccentCyanColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp)).border(1.dp, AccentCyanColor, RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text("App Module", color = AccentCyanColor, fontWeight = FontWeight.Bold)
        }
        Canvas(modifier = Modifier.width(2.dp).height(30.dp)) {
            drawLine(color = TextSecondaryColor, start = Offset(size.width/2, 0f), end = Offset(size.width/2, size.height), strokeWidth = 2f)
        }
        Box(modifier = Modifier.background(AccentVioletColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp)).border(1.dp, AccentVioletColor, RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text("UI Layer", color = AccentVioletColor, fontWeight = FontWeight.Bold)
        }
        Canvas(modifier = Modifier.width(2.dp).height(30.dp)) {
            drawLine(color = TextSecondaryColor, start = Offset(size.width/2, 0f), end = Offset(size.width/2, size.height), strokeWidth = 2f)
        }
        Box(modifier = Modifier.background(Color(0xFF00E676).copy(alpha = 0.2f), RoundedCornerShape(8.dp)).border(1.dp, Color(0xFF00E676), RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text("Domain Layer", color = Color(0xFF00E676), fontWeight = FontWeight.Bold)
        }
        Canvas(modifier = Modifier.width(2.dp).height(30.dp)) {
            drawLine(color = TextSecondaryColor, start = Offset(size.width/2, 0f), end = Offset(size.width/2, size.height), strokeWidth = 2f)
        }
        Box(modifier = Modifier.background(Color(0xFFFFD600).copy(alpha = 0.2f), RoundedCornerShape(8.dp)).border(1.dp, Color(0xFFFFD600), RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text("Data Layer", color = Color(0xFFFFD600), fontWeight = FontWeight.Bold)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(30.dp), modifier = Modifier.padding(top = 30.dp)) {
            Box(modifier = Modifier.background(Color(0xFFFF5252).copy(alpha = 0.2f), RoundedCornerShape(8.dp)).border(1.dp, Color(0xFFFF5252), RoundedCornerShape(8.dp)).padding(16.dp)) {
                Text("Network", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold)
            }
            Box(modifier = Modifier.background(Color(0xFF448AFF).copy(alpha = 0.2f), RoundedCornerShape(8.dp)).border(1.dp, Color(0xFF448AFF), RoundedCornerShape(8.dp)).padding(16.dp)) {
                Text("Database", color = Color(0xFF448AFF), fontWeight = FontWeight.Bold)
            }
        }
    }
}
