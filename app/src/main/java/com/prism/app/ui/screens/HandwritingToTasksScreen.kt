package com.prism.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
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
private val AccentVioletColor = Color(0xFFBB86FC)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandwritingToTasksScreen(onBackClick: () -> Unit = {}) {
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
                title = { Text("Handwriting to Tasks", color = TextPrimaryColor, fontWeight = FontWeight.Bold) },
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
                    0 -> IdleState(onScan = { state = 1 })
                    1 -> ProcessingState()
                    2 -> ResultState(onNewScan = { state = 0 })
                }
            }
        }
    }
}

@Composable
private fun IdleState(onScan: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF5E6).copy(alpha = 0.9f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                for(i in 0..5) {
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Text("Scan your handwritten notes", color = TextSecondaryColor, fontSize = 18.sp)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onScan,
            colors = ButtonDefaults.buttonColors(containerColor = AccentCyanColor, contentColor = BgColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.CameraAlt, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Scan Notes", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }
    }
}

@Composable
private fun ProcessingState() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = AccentVioletColor, modifier = Modifier.size(64.dp), strokeWidth = 6.dp)
        Spacer(Modifier.height(24.dp))
        Text("Reading handwriting...", color = AccentVioletColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

data class TaskItem(val title: String, val type: String, val typeColor: Color, val priority: String, val assignee: String)

@Composable
private fun ResultState(onNewScan: () -> Unit) {
    val tasks = listOf(
        TaskItem("Add dark mode toggle", "Feature", AccentCyanColor, "High", "Alice"),
        TaskItem("Fix crash on rotation", "Bug", Color(0xFFFF5252), "Critical", "Bob"),
        TaskItem("Update dependencies", "Task", Color(0xFFB0B0B0), "Low", "Unassigned"),
        TaskItem("Optimize list scrolling", "Improvement", AccentVioletColor, "Medium", "Alice")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardColor),
                    border = BorderStroke(1.dp, CardBorderColor)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(task.title, color = TextPrimaryColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TextSecondaryColor)
                        }
                        Spacer(Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(task.typeColor.copy(alpha = 0.2f)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                Text(task.type, color = task.typeColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Text("Pri: ${task.priority}", color = TextSecondaryColor, fontSize = 12.sp)
                            Spacer(Modifier.weight(1f))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = TextSecondaryColor, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(task.assignee, color = TextSecondaryColor, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            OutlinedButton(
                onClick = {},
                border = BorderStroke(1.dp, AccentVioletColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AccentVioletColor)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Export")
                Spacer(Modifier.width(8.dp))
                Text("Export to Jira")
            }
            Button(
                onClick = onNewScan,
                colors = ButtonDefaults.buttonColors(containerColor = AccentVioletColor, contentColor = BgColor)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "New Scan")
                Spacer(Modifier.width(8.dp))
                Text("New Scan", fontWeight = FontWeight.Bold)
            }
        }
    }
}
