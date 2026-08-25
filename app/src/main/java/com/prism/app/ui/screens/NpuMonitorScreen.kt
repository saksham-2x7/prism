package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NpuMonitorScreen(onBackClick: () -> Unit = {}) {
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("Neural Core Monitor", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Chip Header
            Icon(
                imageVector = Icons.Default.Memory,
                contentDescription = "NPU",
                tint = Color(0xFF00E5FF),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Snapdragon 8 Elite NPU",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Hexagon Tensor Accelerator",
                color = Color(0xFFB0B0B0),
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // Live Stats Grid
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Inference Speed",
                    icon = Icons.Default.Speed,
                    baseValue = 45.0f,
                    unit = " t/s",
                    variance = 5.0f,
                    color = Color(0xFF00E5FF)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Memory Bandwidth",
                    icon = Icons.Default.Storage,
                    baseValue = 72.0f,
                    unit = " GB/s",
                    variance = 3.0f,
                    color = Color(0xFFBB86FC)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Real-time Load Graph
            Text(
                text = "NPU Workload Allocation",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LiveGraph(color = Color(0xFF00E5FF))
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Footer Info
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF2A2A2A))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Quantization", color = Color(0xFFB0B0B0))
                    Text("INT4 / INT8", color = Color.White, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
            Spacer(modifier = Modifier.height(32.dp)) // space for bottom nav
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, icon: ImageVector, baseValue: Float, unit: String, variance: Float, color: Color) {
    var currentValue by remember { mutableFloatStateOf(baseValue) }
    
    LaunchedEffect(Unit) {
        while (true) {
            delay(800)
            currentValue = baseValue + (Random.nextFloat() * variance * 2) - variance
        }
    }
    
    Card(
        modifier = modifier.height(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = color)
            Text(title, color = Color(0xFFB0B0B0), fontSize = 12.sp)
            Text(
                text = String.format("%.1f%s", currentValue, unit),
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun LiveGraph(color: Color) {
    val barCount = 20
    val heights = remember { mutableStateListOf(*Array(barCount) { Random.nextFloat() * 100f }) }
    
    LaunchedEffect(Unit) {
        while (true) {
            delay(200)
            heights.removeAt(0)
            heights.add(Random.nextFloat() * 80f + 20f)
        }
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0D0D0D))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        heights.forEach { height ->
            val animatedHeight by animateFloatAsState(
                targetValue = height,
                animationSpec = tween(durationMillis = 200, easing = LinearEasing),
                label = "barHeight"
            )
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight(animatedHeight / 100f)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(color)
            )
        }
    }
}
