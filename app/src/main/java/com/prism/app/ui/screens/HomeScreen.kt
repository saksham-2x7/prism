package com.prism.app.ui.screens

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prism.app.ui.components.GlassCard
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HomeScreen(navController: androidx.navigation.NavController) {
    Scaffold(
        containerColor = Color.Black
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Animated Mesh Gradient Background
            AnimatedMeshBackground()

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "NEURON",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 8.sp
                    )
                }

                // Grid
                val features = listOf(
                    Triple("Sketch to Code", "Camera → Compose", "📸") to "sketch_to_code",
                    Triple("Voice to Code", "Speak → Working code", "🎙️") to "voice_to_code",
                    Triple("Bug Reporter", "Screenshot → Report", "🐛") to "bug_reporter",
                    Triple("Notes to Tasks", "Handwriting → Jira", "📝") to "handwriting_to_tasks",
                    Triple("Code Explainer", "Analyze logic", "🔍") to "code_explainer",
                    Triple("Architecture", "Draw diagram", "🏗️") to "architecture_diagram",
                    Triple("Privacy Vault", "Encrypted history", "🔐") to "privacy_vault"
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(features) { (feature, route) ->
                        GlassCard(
                            title = feature.first,
                            subtitle = feature.second,
                            icon = feature.third,
                            onClick = { navController.navigate(route) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(100.dp)) // Space for bottom bar
            }

            // Quick Action Bottom Bar
            GlassBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                navController = navController
            )
        }
    }
}

@Composable
fun AnimatedMeshBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "mesh_bg")
    
    val angle1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle1"
    )
    
    val angle2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle2"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        val radius = width * 0.7f
        
        val x1 = width / 2 + (width * 0.3f) * cos(Math.toRadians(angle1.toDouble())).toFloat()
        val y1 = height / 2 + (height * 0.3f) * sin(Math.toRadians(angle1.toDouble())).toFloat()
        
        val x2 = width / 2 + (width * 0.4f) * cos(Math.toRadians(angle2.toDouble() + 180)).toFloat()
        val y2 = height / 2 + (height * 0.4f) * sin(Math.toRadians(angle2.toDouble() + 180)).toFloat()
        
        val x3 = width / 2 + (width * 0.2f) * cos(Math.toRadians(-angle1.toDouble())).toFloat()
        val y3 = height / 2 + (height * 0.2f) * sin(Math.toRadians(-angle1.toDouble())).toFloat()

        drawCircle(
            color = Color(0xFF00E5FF).copy(alpha = 0.4f), // Cyan
            radius = radius,
            center = Offset(x1, y1)
        )
        
        drawCircle(
            color = Color(0xFFBB86FC).copy(alpha = 0.4f), // Violet
            radius = radius * 1.2f,
            center = Offset(x2, y2)
        )
        
        drawCircle(
            color = Color(0xFF0F2027).copy(alpha = 0.6f), // Dark blue
            radius = radius * 0.9f,
            center = Offset(x3, y3)
        )
    }
}

@Composable
fun GlassBottomBar(modifier: Modifier = Modifier, navController: androidx.navigation.NavController) {
    val shape = RoundedCornerShape(32.dp)
    val borderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF00E5FF).copy(alpha = 0.5f),
            Color.Transparent,
            Color(0xFFBB86FC).copy(alpha = 0.5f)
        )
    )

    Box(
        modifier = modifier
            .width(280.dp)
            .height(64.dp)
            .clip(shape)
            .border(1.dp, borderBrush, shape)
            .background(Color.White.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color(0xFF00E5FF)
                )
            }
            
            IconButton(
                onClick = { navController.navigate("voice_to_code") },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice",
                    tint = Color.White
                )
            }
            
            IconButton(
                onClick = { navController.navigate("privacy_vault") },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "History",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { navController.navigate("settings") },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }
    }
}
