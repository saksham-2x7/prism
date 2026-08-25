package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prism.app.ui.components.GlassCard
import kotlin.math.cos
import kotlin.math.sin

data class FeatureDef(val title: String, val subtitle: String, val icon: String, val route: String, val category: String)

@Composable
fun HomeScreen(navController: NavController) {
    val categories = listOf("All Tools", "Generative AI", "Vision AI", "System")
    var selectedTab by remember { mutableIntStateOf(0) }

    val allFeatures = listOf(
        FeatureDef("Code Explainer", "Analyze logic", "🔍", "code_explainer", "Generative AI"),
        FeatureDef("Sketch to Code", "Camera → Compose", "📸", "sketch_to_code", "Vision AI"),
        FeatureDef("Voice to Code", "Speak → Working code", "🎙️", "voice_to_code", "Generative AI"),
        FeatureDef("Architecture", "Draw diagram", "🏗️", "architecture_diagram", "System"),
        FeatureDef("Bug Reporter", "Screenshot → Report", "🐛", "bug_reporter", "Vision AI"),
        FeatureDef("Notes to Tasks", "Handwriting → Jira", "📝", "handwriting_to_tasks", "Vision AI"),
        FeatureDef("Privacy Vault", "Encrypted history", "🔐", "privacy_vault", "System"),
        FeatureDef("API Generator", "REST from models", "🔌", "api_gen", "Generative AI"),
        FeatureDef("UI Debugger", "View Hierarchy", "📱", "ui_debug", "System"),
        FeatureDef("Local LLM", "Chat with codebase", "🤖", "local_llm", "Generative AI")
    )

    val displayedFeatures = if (selectedTab == 0) allFeatures else allFeatures.filter { it.category == categories[selectedTab] }

    Scaffold(
        containerColor = Color.Black
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Safe Aurora Background
            SafeAuroraBackground()

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

                // Scrollable Tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    edgePadding = 24.dp,
                    divider = {},
                    indicator = { tabPositions ->
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTab])
                                .height(4.dp)
                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                .background(Color(0xFF00E5FF))
                        )
                    }
                ) {
                    categories.forEachIndexed { index, title ->
                        val selected = selectedTab == index
                        Tab(
                            selected = selected,
                            onClick = { selectedTab = index },
                            text = { 
                                Text(
                                    text = title, 
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selected) Color.White else Color(0xFF888888),
                                    fontSize = 16.sp
                                ) 
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                // Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(displayedFeatures, key = { it.title }) { feature ->
                        GlassCard(
                            title = feature.title,
                            subtitle = feature.subtitle,
                            icon = feature.icon,
                            onClick = { 
                                try { navController.navigate(feature.route) } catch(e: Exception) {} 
                            },
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
fun SafeAuroraBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "aurora")
    
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
        
        val radius1 = width * 1.2f
        val radius2 = width * 1.4f
        
        val x1 = width / 2 + (width * 0.4f) * cos(Math.toRadians(angle1.toDouble())).toFloat()
        val y1 = height / 3 + (height * 0.3f) * sin(Math.toRadians(angle1.toDouble())).toFloat()
        
        val x2 = width / 2 + (width * 0.5f) * cos(Math.toRadians(angle2.toDouble() + 180)).toFloat()
        val y2 = height * 0.6f + (height * 0.4f) * sin(Math.toRadians(angle2.toDouble() + 180)).toFloat()
        
        // Massive radial gradients simulate the blurred aurora perfectly without crashing
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF00E5FF).copy(alpha = 0.25f), Color.Transparent),
                center = Offset(x1, y1),
                radius = radius1
            ),
            radius = radius1,
            center = Offset(x1, y1)
        )
        
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFBB86FC).copy(alpha = 0.2f), Color.Transparent),
                center = Offset(x2, y2),
                radius = radius2
            ),
            radius = radius2,
            center = Offset(x2, y2)
        )
    }
}

@Composable
fun GlassBottomBar(modifier: Modifier = Modifier, navController: NavController) {
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
            .width(340.dp)
            .height(72.dp)
            .clip(shape)
            .border(1.dp, borderBrush, shape)
            .background(Color(0xFF111111).copy(alpha = 0.8f)), // solid fallback
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier.size(48.dp).clip(CircleShape)
            ) {
                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
            }
            
            IconButton(
                onClick = { navController.navigate("bug_reporter") },
                modifier = Modifier.size(48.dp).clip(CircleShape)
            ) {
                Icon(Icons.Default.BugReport, contentDescription = "Debugger", tint = Color.White)
            }
            
            // Glowing Neural Core Center Button
            IconButton(
                onClick = { navController.navigate("npu_monitor") },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00E5FF).copy(alpha = 0.2f))
                    .border(1.5.dp, Color(0xFF00E5FF), CircleShape)
            ) {
                Icon(Icons.Default.Memory, contentDescription = "Neural Core", tint = Color(0xFF00E5FF), modifier = Modifier.size(32.dp))
            }
            
            IconButton(
                onClick = { navController.navigate("privacy_vault") },
                modifier = Modifier.size(48.dp).clip(CircleShape)
            ) {
                Icon(Icons.Default.Lock, contentDescription = "Vault", tint = Color.White)
            }

            IconButton(
                onClick = { navController.navigate("settings") },
                modifier = Modifier.size(48.dp).clip(CircleShape)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
            }
        }
    }
}
